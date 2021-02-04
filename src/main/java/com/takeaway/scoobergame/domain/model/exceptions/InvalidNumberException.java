package com.takeaway.scoobergame.domain.model.exceptions;

import com.takeaway.scoobergame.domain.model.valueobjects.ErrorCode;
import org.springframework.http.HttpStatus;

public class InvalidNumberException extends DomainException {

    private static final long serialVersionUID = 1L;

    public InvalidNumberException() {
        super(ErrorCode.INVALID_NUMBER_MOVE.getMessage(), ErrorCode.INVALID_NUMBER_MOVE, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
