package com.takeaway.scoobergame.domain.model.exceptions;

import com.takeaway.scoobergame.domain.model.valueobjects.ErrorCode;
import org.springframework.http.HttpStatus;

public class InvalidTurnException extends DomainException {
    private static final long serialVersionUID = 1L;

    public InvalidTurnException() {
        super(ErrorCode.INVALID_TURN_MOVE.getMessage(), ErrorCode.INVALID_TURN_MOVE, HttpStatus.BAD_REQUEST);
    }
}
