package com.ramostear.unaboot.common.exception;

/**
 * @ ClassName AlreadyExistsException
 * @ Description TODO
 * @ Author ramostear
 * @ Date 2019/11/13 0013 1:32
 * @ Version 1.0
 **/
public class AlreadyExistsException extends BadRequestException {

    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
