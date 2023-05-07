package com.husky.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.husky.base.exception.XueChengException;
import com.husky.ucenter.feignclient.CheckCodeClient;
import com.husky.ucenter.mapper.XcUserMapper;
import com.husky.ucenter.model.dto.AuthParamsDto;
import com.husky.ucenter.model.dto.XcUserExt;
import com.husky.ucenter.model.po.XcUser;
import com.husky.ucenter.service.AuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/29
 * Time: 21:14
 * Description: 账号密码方式登录
 */
@Service("password_authService")
public class PasswordAuthServiceImpl implements AuthService {

    @Autowired
    private XcUserMapper xcUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CheckCodeClient checkCodeClient;

    @Override
    public XcUserExt execute(AuthParamsDto authParamsDto) {

        // 账号
        String username = authParamsDto.getUsername();

        // 校验密码
        // 输入的验证码
        String checkCode = authParamsDto.getCheckcode();

        // 验证码对应的key
        String checkCodeKey = authParamsDto.getCheckcodekey();

        if (StringUtils.isEmpty(checkCode) || StringUtils.isEmpty(checkCodeKey)) {
            throw new XueChengException("请输入验证码");
        }

        // 远程调用验证码服务接口校验验证码
        Boolean verify = checkCodeClient.verify(checkCodeKey, checkCode);
        if (verify == null || !verify) {
            throw new XueChengException("验证码输入错误");
        }
        LambdaQueryWrapper<XcUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(XcUser::getUsername, username);
        // 根据username查询用户
        XcUser xcUser = xcUserMapper.selectOne(queryWrapper);

        // 查询用户不存在，返回空即可， spring Security框架抛出异常用户不存在
        if (xcUser == null) {
            throw new XueChengException("账号不存在");
        }


        // 从数据库拿到用户正确密码
        String passwordDB = xcUser.getPassword();
        // 从表单上拿到的密码
        String passwordFrom = authParamsDto.getPassword();

        // 校验密码是否正确
        boolean matches = passwordEncoder.matches(passwordFrom, passwordDB);
        if (!matches) {
            throw new XueChengException("账号或密码错误");
        }

        XcUserExt xcUserExt = new XcUserExt();
        BeanUtils.copyProperties(xcUser, xcUserExt);
        return xcUserExt;
    }
}
