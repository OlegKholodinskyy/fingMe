package com.findme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Not found")  // 404
public class NotFoundException extends Exception{
    public NotFoundException(String message){
        super(message);
    }
}
