package com.takeaway.scoobergame.domain.model.valueobjects;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerRegistration {
    private String gameId;
    private String playerId;
}
