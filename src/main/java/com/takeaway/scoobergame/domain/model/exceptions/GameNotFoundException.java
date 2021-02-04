package com.takeaway.scoobergame.domain.model.exceptions;

import com.takeaway.scoobergame.domain.model.valueobjects.ErrorCode;
import org.springframework.http.HttpStatus;

public class GameNotFoundException extends DomainException {

    public GameNotFoundException() {
        super(ErrorCode.GAME_NOT_FOUND.getMessage(), ErrorCode.GAME_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}
