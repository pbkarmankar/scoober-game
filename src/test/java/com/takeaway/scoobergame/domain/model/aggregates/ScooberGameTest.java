package com.takeaway.scoobergame.domain.model.aggregates;

import com.takeaway.scoobergame.domain.model.exceptions.GameAlreadyFinishedException;
import com.takeaway.scoobergame.domain.model.exceptions.GameNotFoundException;
import com.takeaway.scoobergame.domain.model.exceptions.InvalidNumberException;
import com.takeaway.scoobergame.domain.model.exceptions.InvalidTurnException;
import com.takeaway.scoobergame.domain.model.valueobjects.Move;
import com.takeaway.scoobergame.interfaces.resources.NextMove;
import com.takeaway.scoobergame.domain.model.valueobjects.PlayerRegistration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ScooberGameTest {

    private ScooberGame scooberGame = new ScooberGame();

    @Test
    public void registerPlayer_ShouldRegisterFirstNewPlayerAndReturnGameIdAndPlayerId() {
        // Method under test.
        PlayerRegistration playerRegistrationActual = scooberGame.registerPlayer();

        // Assertions.
        assertThat(playerRegistrationActual, is(notNullValue()));
        assertThat(playerRegistrationActual.getGameId(), is(equalTo("G1")));
        assertThat(playerRegistrationActual.getPlayerId(), is(equalTo("P1")));
    }

    @Test
    public void registerPlayer_ShouldRegisterSecondNewPlayerAndReturnGameIdAndPlayerId() {
        // Method under test.
        PlayerRegistration playerRegistrationActual = scooberGame.registerPlayer();
        playerRegistrationActual = scooberGame.registerPlayer();

        // Assertions.
        assertThat(playerRegistrationActual, is(notNullValue()));
        assertThat(playerRegistrationActual.getGameId(), is(equalTo("G1")));
        assertThat(playerRegistrationActual.getPlayerId(), is(equalTo("P2")));
    }

    @Test
    public void getEmitterForPlayer_ShouldReturnSseEmitterForFirstPlayer() {
        // Prepare test data.
        PlayerRegistration playerRegistration = scooberGame.registerPlayer();

        // Method under test.
        SseEmitter sseEmitterActual = scooberGame.getEmitterForPlayer(playerRegistration);

        // Assertions.
        assertThat(sseEmitterActual, is(notNullValue()));
    }

    @Test
    public void getEmitterForPlayer_ShouldReturnSseEmitterForSecondPlayer() {
        // Prepare test data.
        PlayerRegistration playerRegistration = scooberGame.registerPlayer();
        playerRegistration = scooberGame.registerPlayer();

        // Method under test.
        SseEmitter sseEmitterActual = scooberGame.getEmitterForPlayer(playerRegistration);

        // Assertions.
        assertThat(sseEmitterActual, is(notNullValue()));
    }

    @Test
    public void getEmitterForPlayer_ShouldThrowsGameNotFoundException() {
        // Prepare test data.
        PlayerRegistration playerRegistration = new PlayerRegistration("G1", "P1");

        // Method under test.
        assertThrows(GameNotFoundException.class, () -> {
            SseEmitter sseEmitterActual = scooberGame.getEmitterForPlayer(playerRegistration);
        });
    }

    @Test
    public void playNextMove_ShouldReturnMoveDetailsGivenNextMoveDetails() throws IOException {
        // Prepare test data.
        PlayerRegistration playerRegistration = scooberGame.registerPlayer();
        NextMove nextMove = new NextMove("G1", "P1", 100);
        Move moveExpected = new Move("P1", 100, 33, -1, false);

        // Method under test.
        Move moveActual = scooberGame.nextMove(nextMove);

        // Assertions.
        assertThat(moveActual, is(notNullValue()));
        assertThat(moveActual.getPlayer(), is(equalTo(moveExpected.getPlayer())));
        assertThat(moveActual.getNumber(), is(equalTo(moveExpected.getNumber())));
        assertThat(moveActual.getNextNumber(), is(equalTo(moveExpected.getNextNumber())));
        assertThat(moveActual.getAdded(), is(equalTo(moveExpected.getAdded())));
        assertThat(moveActual.isGameFinished(), is(equalTo(moveExpected.isGameFinished())));
    }

    @Test
    public void playNextMove_ShouldThrowInvalidTurnException() throws IOException {
        // Prepare test data.
        PlayerRegistration playerRegistration = scooberGame.registerPlayer();
        playerRegistration = scooberGame.registerPlayer();
        NextMove nextMove = new NextMove("G1", "P2", 100);

        // Method under test.
        assertThrows(InvalidTurnException.class, () -> {
            Move moveActual = scooberGame.nextMove(nextMove);
        });
    }

    @Test
    public void playNextMove_ShouldThrowInvalidNumberException() throws IOException {
        // Prepare test data.
        PlayerRegistration playerRegistration = scooberGame.registerPlayer();
        playerRegistration = scooberGame.registerPlayer();
        NextMove nextMove1 = new NextMove("G1", "P1", 100);
        scooberGame.nextMove(nextMove1);
        NextMove nextMove2 = new NextMove("G1", "P2", 30);

        // Method under test.
        assertThrows(InvalidNumberException.class, () -> {
            Move moveActual = scooberGame.nextMove(nextMove2);
        });
    }

    @Test
    public void playNextMove_ShouldThrowGameAlreadyFinishedException() throws IOException {
        // Prepare test data.
        PlayerRegistration playerRegistration = scooberGame.registerPlayer();
        playerRegistration = scooberGame.registerPlayer();
        NextMove nextMove1 = new NextMove("G1", "P1", 3);
        scooberGame.nextMove(nextMove1);
        NextMove nextMove2 = new NextMove("G1", "P2", 3);

        // Method under test.
        assertThrows(GameAlreadyFinishedException.class, () -> {
            Move moveActual = scooberGame.nextMove(nextMove2);
        });
    }

    @Test
    public void playNextMove_ShouldThrowGameNotFoundException() throws IOException {
        // Prepare test data.
        NextMove nextMove = new NextMove("G1", "P1", 100);

        // Method under test.
        assertThrows(GameNotFoundException.class, () -> {
            Move moveActual = scooberGame.nextMove(nextMove);
        });
    }
}
