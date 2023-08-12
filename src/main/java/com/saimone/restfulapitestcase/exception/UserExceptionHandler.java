package com.saimone.restfulapitestcase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(value = {UserNotFoundException.class})
    public UserException handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        return new UserException(
                userNotFoundException.getMessage(),
                userNotFoundException.getCause(),
                HttpStatus.NOT_FOUND
        );
    }
}
