package com.takeaway.scoobergame.domain.model.exceptions;

import com.takeaway.scoobergame.domain.model.valueobjects.ErrorCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class GameAlreadyFinishedException extends DomainException {
    private static final long serialVersionUID = 1L;

    public GameAlreadyFinishedException() {
        super(ErrorCode.GAME_ALREADY_FINISHED.getMessage(), ErrorCode.GAME_ALREADY_FINISHED, HttpStatus.BAD_REQUEST);
    }
}
