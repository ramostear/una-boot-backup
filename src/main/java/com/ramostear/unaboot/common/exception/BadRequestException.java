package com.ramostear.unaboot.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @ClassName BadRequestException
 * @Description 请求错误异常
 * @Author ramostear
 * @Date 2019/11/8 0008 23:17
 * @Version 1.0
 **/
public class BadRequestException extends UnaException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
