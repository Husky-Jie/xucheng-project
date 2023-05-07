package com.husky.ucenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.husky.base.exception.XueChengException;
import com.husky.ucenter.mapper.XcMenuMapper;
import com.husky.ucenter.mapper.XcUserMapper;
import com.husky.ucenter.model.dto.AuthParamsDto;
import com.husky.ucenter.model.dto.XcUserExt;
import com.husky.ucenter.model.po.XcMenu;
import com.husky.ucenter.model.po.XcUser;
import com.husky.ucenter.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/25
 * Time: 20:55
 * Description: TODO
 */
@Component("UserServiceImpl")
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private XcMenuMapper xcMenuMapper;

    /**
     * 根据账号查询用户信息
     * @param authParams  传入的请求认证参数就是AuthParamsDto
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String authParams) throws UsernameNotFoundException {

        AuthParamsDto authParamsDto = null;
        try {
            // 将传入的json转为AuthParamsDto对象
            authParamsDto = JSON.parseObject(authParams, AuthParamsDto.class);
        } catch (Exception e) {
            throw new XueChengException("请求认证不符合要求");
        }

        // 登录类型
        String authType = authParamsDto.getAuthType();

        // 根据不同的登录类型从spring容器中选取指定的bean
        String beanName = authType + "_authService";
        AuthService authService = applicationContext.getBean(beanName, AuthService.class);

        // 调用统一的execute方法完成认证
        XcUserExt xcUserExt = authService.execute(authParamsDto);

        // 封装XcUserExt用户信息为UserDetails
        UserDetails userDetails = getUserDetails(xcUserExt);

        return userDetails;
    }

    private UserDetails getUserDetails(XcUserExt xcUserExt) {
        // 用户权限, 不设置会报错
        String[] authorities = null;
        // 根据用户id查询用户权限
        List<XcMenu> xcMenus = xcMenuMapper.selectPermissionByUserId(xcUserExt.getId());
        // 获取用户权限码
        List<String> permissions = new ArrayList<>();
        xcMenus.forEach(i->{
            permissions.add(i.getCode());
        });
        // 将权限码集合转为数组
        authorities = permissions.toArray(new String[0]);
        // 用户密码
        String password = xcUserExt.getPassword();
        xcUserExt.setPassword(null);
        // 转为json
        String json = JSON.toJSONString(xcUserExt);
        // 若不为空，拿到正确的密码，最终封装成UserDetails对象给springSecurity框架返回，由框架进行密码比对
        UserDetails userDetails = User.withUsername(json).password(password).authorities(authorities).build();
        return userDetails;
    }
}
