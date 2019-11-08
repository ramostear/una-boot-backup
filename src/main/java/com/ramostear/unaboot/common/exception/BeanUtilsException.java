package com.ramostear.unaboot.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @ClassName BeanUtilsException
 * @Description 服务器内部异常
 * @Author ramostear
 * @Date 2019/11/8 0008 23:18
 * @Version 1.0
 **/
public class BeanUtilsException extends UnaException{

    public BeanUtilsException(String message) {
        super(message);
    }

    public BeanUtilsException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
