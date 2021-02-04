package com.takeaway.scoobergame.domain.model.valueobjects;

public enum  ErrorCode {
    GAME_ALREADY_FINISHED("400", "The game is over. No more moves are allowed."),

    INVALID_NUMBER_MOVE("422", "This number is not allowed in the sequence of numbers."),

    INVALID_TURN_MOVE("400", "It is not your turn. Wait for the other player's move."),

    GAME_NOT_FOUND("404", "Game is invalid, Provide correct game id");

    private String code;

    private String message;

    private ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
