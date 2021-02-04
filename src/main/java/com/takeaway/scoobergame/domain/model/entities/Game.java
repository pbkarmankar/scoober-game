package com.takeaway.scoobergame.domain.model.entities;

import com.takeaway.scoobergame.domain.model.valueobjects.Move;
import lombok.Data;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Data
public class Game {
    private String gameId;
    private SseEmitter player1;
    private SseEmitter player2;
    private Player nextMovePlayer;
    private Move lastMove;

    public Game(String gameId) {
        this.gameId = gameId;
        this.setPlayer1(new SseEmitter(-1L));
        this.setPlayer2(new SseEmitter(-1L));
    }
}
