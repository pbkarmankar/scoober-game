package com.takeaway.scoobergame.interfaces.rest;

import com.takeaway.scoobergame.domain.model.exceptions.DomainException;
import com.takeaway.scoobergame.interfaces.resources.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdviser extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Error> handleAppException(DomainException domainException) {
        Error error = new Error(domainException.getErrorCode());
        return new ResponseEntity<Error>(error, domainException.getHttpStatus());
    }
}
