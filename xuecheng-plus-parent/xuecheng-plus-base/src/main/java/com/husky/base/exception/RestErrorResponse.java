package com.husky.base.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/9
 * Time: 20:41
 * Description: 响应用户的统一异常类型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestErrorResponse extends Throwable implements Serializable {

    private String errCode;

    private String errMessage;

    public RestErrorResponse(String errMessage){
        this.errMessage= errMessage;
    }

}
