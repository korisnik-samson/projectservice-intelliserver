package com.samson.projectserviceintelliserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthourizedException extends RuntimeException {
    public UnauthourizedException(String message) {
        super(message);
    }
}
