package com.takeaway.scoobergame.application;

import com.takeaway.scoobergame.domain.model.aggregates.ScooberGame;
import com.takeaway.scoobergame.domain.model.valueobjects.Move;
import com.takeaway.scoobergame.interfaces.resources.NextMove;
import com.takeaway.scoobergame.domain.model.valueobjects.PlayerRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

public class ScooberGameService {

    @Autowired
    private ScooberGame scooberGame;

    public PlayerRegistration registerPlayer() {
        return scooberGame.registerPlayer();
    }

    public SseEmitter registerGame(PlayerRegistration playerRegistration) {
        return scooberGame.getEmitterForPlayer(playerRegistration);
    }

    public Move playNextMove(NextMove nextMove) throws IOException {
        return scooberGame.nextMove(nextMove);
    }
}
