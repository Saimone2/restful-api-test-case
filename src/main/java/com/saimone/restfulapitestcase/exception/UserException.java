package com.saimone.restfulapitestcase.exception;

import org.springframework.http.HttpStatus;

public record UserException(String message, Throwable throwable, HttpStatus httpStatus) {
}
