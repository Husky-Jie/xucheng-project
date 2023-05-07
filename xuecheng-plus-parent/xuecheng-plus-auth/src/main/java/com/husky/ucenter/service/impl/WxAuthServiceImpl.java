package com.husky.ucenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.husky.base.exception.XueChengException;
import com.husky.ucenter.mapper.XcRoleMapper;
import com.husky.ucenter.mapper.XcUserMapper;
import com.husky.ucenter.mapper.XcUserRoleMapper;
import com.husky.ucenter.model.dto.AuthParamsDto;
import com.husky.ucenter.model.dto.XcUserExt;
import com.husky.ucenter.model.po.XcUser;
import com.husky.ucenter.model.po.XcUserRole;
import com.husky.ucenter.service.AuthService;
import com.husky.ucenter.service.WxAuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/29
 * Time: 21:15
 * Description: 微信扫码方式登录
 */
@Service("wx_authService")
public class WxAuthServiceImpl implements AuthService, WxAuthService {

    @Autowired
    private XcUserMapper xcUserMapper;

    @Autowired
    private XcUserRoleMapper xcUserRoleMapper;

    @Autowired
    private WxAuthServiceImpl wxAuthService;

    @Value("${weixin.appid}")
    String appid;

    /**
     * 密钥
     */
    @Value("${weixin.secret}")
    String secret;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public XcUserExt execute(AuthParamsDto authParamsDto) {

        String username = authParamsDto.getUsername();

        LambdaQueryWrapper<XcUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(XcUser::getUsername, username);
        // 根据username查询用户
        XcUser xcUser = xcUserMapper.selectOne(queryWrapper);

        if (xcUser == null) {
            throw new XueChengException("用户不存在");
        }
        XcUserExt xcUserExt = new XcUserExt();
        BeanUtils.copyProperties(xcUser, xcUserExt);
        return xcUserExt;
    }

    @Override
    public XcUser wxAuth(String code) {

        // 申请令牌
        Map<String, String> access_token_map = getAccess_token(code);
        // 获取令牌
        String token = access_token_map.get("access_token");
        // 获取openid
        String openid = access_token_map.get("openid");
        // 携带令牌查询用户信息
        Map<String, String> userInfo = getUserInfo(token, openid);
        // 保存用户信息到数据库 在方法中调用开启事务的方法需要注入自己本身，本身调用方法，事务才会生效
        XcUser xcUser = wxAuthService.addWxUser(userInfo);

        return xcUser;
    }

    /**
     * 携带授权码申请令牌
     * https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
     * 申请令牌的返回结果(令牌)
     * {
     * "access_token":"ACCESS_TOKEN",
     * "expires_in":7200,
     * "refresh_token":"REFRESH_TOKEN",
     * "openid":"OPENID",
     * "scope":"SCOPE",
     * "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
     * }
     * @param code 授权码
     * @return
     */
    private Map<String, String> getAccess_token(String code) {

        String url_template = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
        // 最终请求的url
        String url = String.format(url_template, appid, secret, code);

        // 远程调用此url
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, null, String.class);

        // 获取相应结果
        String result = exchange.getBody();

        // 转为map
        Map<String, String> map = JSON.parseObject(result, Map.class);
        return map;
    }

    /**
     * 携带令牌查询用户信息
     *
     * 响应结果
     *{
     * "openid":"OPENID",
     * "nickname":"NICKNAME",
     * "sex":1,
     * "province":"PROVINCE",
     * "city":"CITY",
     * "country":"COUNTRY",
     * "headimgurl": "https://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
     * "privilege":[
     * "PRIVILEGE1",
     * "PRIVILEGE2"
     * ],
     * "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
     *
     * }
     * @param access_token
     * @param openid
     * @return
     */
    private Map<String, String> getUserInfo(String access_token, String openid) {

        String url_template = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";

        // 最终请求的url
        String url = String.format(url_template, access_token, openid);

        // 远程调用此url
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, null, String.class);

        // 解决中文乱码问题
        String result = new String(exchange.getBody().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        // 转为map
        Map<String, String> map = JSON.parseObject(result, Map.class);
        return map;
    }

    @Transactional
    public XcUser addWxUser(Map<String, String> userInfo){

        // 获取unionid
        String unionid = userInfo.get("unionid");

        // 根据unionid查询用户信息
        LambdaQueryWrapper<XcUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(XcUser::getWxUnionid, unionid);
        XcUser xcUser = xcUserMapper.selectOne(queryWrapper);

        if (xcUser != null) {
            return xcUser;
        }
        String userId = UUID.randomUUID().toString();
        xcUser = new XcUser();
        xcUser.setId(userId);
        xcUser.setWxUnionid(unionid);
        //记录从微信得到的昵称
        xcUser.setNickname(userInfo.get("nickname").toString());
        xcUser.setUserpic(userInfo.get("headimgurl").toString());
        xcUser.setName(userInfo.get("nickname").toString());
        xcUser.setUsername(unionid);
        xcUser.setPassword(unionid);
        xcUser.setUtype("101001");//学生类型
        xcUser.setStatus("1");//用户状态
        xcUser.setCreateTime(LocalDateTime.now());
        xcUserMapper.insert(xcUser);

        // 插入用户权限信息
        XcUserRole xcUserRole = new XcUserRole();
        xcUserRole.setId(UUID.randomUUID().toString());
        xcUserRole.setUserId(userId);
        xcUserRole.setRoleId("17");//学生角色
        xcUserRole.setCreator("学生");
        xcUserRole.setCreateTime(LocalDateTime.now());
        xcUserRoleMapper.insert(xcUserRole);
        return xcUser;

    }
}
