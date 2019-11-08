package com.ramostear.unaboot.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @ClassName NotFoundException
 * @Description 资源未找到异常
 * @Author ramostear
 * @Date 2019/11/8 0008 23:21
 * @Version 1.0
 **/
public class NotFoundException extends UnaException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
