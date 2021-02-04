package com.takeaway.scoobergame.domain.model.exceptions;

import com.takeaway.scoobergame.domain.model.valueobjects.ErrorCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class DomainException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private ErrorCode errorCode;

    private HttpStatus httpStatus;

    public DomainException(String message, ErrorCode errorCode, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}
