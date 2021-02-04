package com.takeaway.scoobergame.interfaces.rest;

import com.takeaway.scoobergame.application.ScooberGameService;
import com.takeaway.scoobergame.domain.model.exceptions.GameAlreadyFinishedException;
import com.takeaway.scoobergame.domain.model.exceptions.InvalidNumberException;
import com.takeaway.scoobergame.domain.model.exceptions.InvalidTurnException;
import com.takeaway.scoobergame.domain.model.valueobjects.Move;
import com.takeaway.scoobergame.interfaces.resources.NextMove;
import com.takeaway.scoobergame.domain.model.valueobjects.PlayerRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private ScooberGameService scooberGameService;

    @PostMapping(value = "/registerplayer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerRegistration> registerPlayer() {
        PlayerRegistration playerRegistration = scooberGameService.registerPlayer();
        return ResponseEntity.ok(playerRegistration);
    }

    @GetMapping(value = "registergame/{gameId}/{playerId}")
    public SseEmitter registerGame(@PathVariable String gameId, @PathVariable String playerId) {
        return scooberGameService.registerGame(new PlayerRegistration(gameId, playerId));
    }

    @PostMapping(value = "playNextMove", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Move> playNextMove(@RequestBody NextMove nextMove)
            throws IOException, InvalidTurnException, GameAlreadyFinishedException, InvalidNumberException {
        Move nextMoved = scooberGameService.playNextMove(nextMove);
        return ResponseEntity.ok(nextMoved);
    }
}
