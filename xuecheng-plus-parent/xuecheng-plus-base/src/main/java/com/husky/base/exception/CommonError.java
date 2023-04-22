package com.husky.base.exception;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/9
 * Time: 20:40
 * Description: 通用异常信息
 */
public enum CommonError {
    /*UNKOWN_ERROR("执行过程异常，请重试。"),
    PARAMS_ERROR("非法参数"),
    OBJECT_NULL("对象为空"),
    QUERY_NULL("查询结果为空"),
    REQUEST_NULL("请求参数为空");*/

    HAS_CHILDNODES("120409", "课程计划信息还有子级信息，无法操作"),
    UNKOWN_ERROR("","执行过程异常，请重试。");

    private String errCode;

    private String errMessage;

    CommonError(String errCode, String errMessage) {
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    @Override
    public String toString() {
        return "{" +
                "errCode='" + errCode + '\'' +
                ", errMessage='" + errMessage + '\'' +
                '}';
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }
}
