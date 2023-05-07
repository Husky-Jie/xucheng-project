package com.husky.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/29
 * Time: 20:44
 * Description: 自定义DaoAuthenticationProvider，重写密码比对方法，
 */
@Component
public class DaoAuthenticationProviderCustom extends DaoAuthenticationProvider {

    /**
     * 设置userDetailsService子实现类UserServiceImpl对象
     * @param userDetailsService @Qualifier用来区分存在多个bean
     */
    @Autowired
    @Override
    public void setUserDetailsService(@Qualifier("UserServiceImpl") UserDetailsService userDetailsService) {
        super.setUserDetailsService(userDetailsService);
    }


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }
}
