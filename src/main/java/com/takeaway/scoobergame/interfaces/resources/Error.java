package com.takeaway.scoobergame.interfaces.resources;

import com.takeaway.scoobergame.domain.model.valueobjects.ErrorCode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Error {

    private String errorCode;

    private String errorMessage;

    public Error(ErrorCode validationError) {
        this.errorCode = validationError.getCode();
        this.errorMessage = validationError.getMessage();
    }
}
