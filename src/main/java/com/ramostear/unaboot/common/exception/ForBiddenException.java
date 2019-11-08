package com.ramostear.unaboot.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @ClassName ForBiddenException
 * @Description 拒绝请求异常
 * @Author ramostear
 * @Date 2019/11/8 0008 23:20
 * @Version 1.0
 **/
public class ForBiddenException extends UnaException {

    public ForBiddenException(String message) {
        super(message);
    }

    public ForBiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
