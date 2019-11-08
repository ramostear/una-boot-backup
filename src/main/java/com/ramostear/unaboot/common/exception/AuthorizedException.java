package com.ramostear.unaboot.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @ClassName AuthenticationException
 * @Description 权限异常
 * @Author ramostear
 * @Date 2019/11/8 0008 23:23
 * @Version 1.0
 **/
public class AuthorizedException extends UnaException {

    public AuthorizedException(String message) {
        super(message);
    }

    public AuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
