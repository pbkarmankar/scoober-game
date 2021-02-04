package com.takeaway.scoobergame.interfaces.resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NextMove {
    private String gameId;
    private String playerId;
    private Integer number;
}
