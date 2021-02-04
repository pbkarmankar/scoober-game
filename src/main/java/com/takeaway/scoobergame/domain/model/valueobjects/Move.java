package com.takeaway.scoobergame.domain.model.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Move {
    private String player;
    private Integer number;
    private Integer nextNumber;
    private Integer added;
    private boolean gameFinished;
}
