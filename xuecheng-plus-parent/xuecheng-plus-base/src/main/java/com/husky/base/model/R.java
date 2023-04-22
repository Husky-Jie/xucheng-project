package com.husky.base.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/13
 * Time: 15:26
 * Description: 统一返回结果类
 */
@Data
@ToString
public class R<T> implements Serializable {

    private String code;

    private String message;

    private T data;

    public static <T> R<T> success(T data, String code, String message){
        R<T> r = new R<>();
        r.code = code;
        r.message = message;
        r.data = data;
        return r;
    }

    public static  <T> R<T> error(String code, String message){
        R<T> r = new R<>();
        r.code = code;
        r.message = message;
        return r;
    }
}
