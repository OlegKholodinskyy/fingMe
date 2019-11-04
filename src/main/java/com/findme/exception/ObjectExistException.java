package com.findme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "BAD_REQUEST")
public class ObjectExistException extends Exception {

    public ObjectExistException(String cause) {
        super(cause);
    }
}
