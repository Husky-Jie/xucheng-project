package com.husky.ucenter.service;

import com.husky.ucenter.model.po.XcUser;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/5/6
 * Time: 20:54
 * Description: 微信扫描认证接口
 */
public interface WxAuthService {

    /**
     * 微信扫码认证，申请令牌，携带令牌查询用户信息、保存用户信息到数据库
     * @param code 授权码
     * @return
     */
    XcUser wxAuth(String code);
}
