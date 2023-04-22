package com.husky.base.exception;

import lombok.Data;
import lombok.ToString;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/9
 * Time: 20:36
 * Description: 描述该类的功能
 */
@Data
@ToString
public class XueChengException extends RuntimeException{

    private String errMessage;

    public XueChengException() {
        super();
    }

    public XueChengException(String errMessage) {
        super(errMessage);
        this.errMessage = errMessage;
    }

    public static void cast(CommonError commonError){
        throw new XueChengException(commonError.getErrMessage());
    }

    public static void cast(String errCode, String errMessage){
        throw new XueChengException(errMessage);
    }

}
