package com.ramostear.unaboot.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @ClassName UnaException
 * @Description 抽象的异常类
 * @Author ramostear
 * @Date 2019/11/8 0008 23:15
 * @Version 1.0
 **/
public abstract class UnaException extends RuntimeException {
    private Object error;

    public UnaException(String message){
        super(message);
    }

    public UnaException(String message,Throwable cause){
        super(message,cause);
    }

    @NonNull
    public abstract HttpStatus getStatus();

    public Object getError(){
        return error;
    }

    public UnaException setError(@Nullable Object error){
        this.error = error;
        return this;
    }
}
