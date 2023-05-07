package com.husky.ucenter.service;

import com.husky.ucenter.model.dto.AuthParamsDto;
import com.husky.ucenter.model.dto.XcUserExt;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/29
 * Time: 21:10
 * Description: 统一认证的接口
 */
public interface AuthService {

    /**
     * @description 认证方法
     * @param authParamsDto 认证参数
     * @return com.xuecheng.ucenter.model.po.XcUser 用户信息
     * @author Mr.M
     * @date 2022/9/29 12:11
     */
    XcUserExt execute(AuthParamsDto authParamsDto);

}
