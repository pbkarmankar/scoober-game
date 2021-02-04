package com.takeaway.scoobergame.application;

import com.takeaway.scoobergame.domain.model.aggregates.ScooberGame;
import com.takeaway.scoobergame.domain.model.exceptions.GameAlreadyFinishedException;
import com.takeaway.scoobergame.domain.model.exceptions.GameNotFoundException;
import com.takeaway.scoobergame.domain.model.exceptions.InvalidNumberException;
import com.takeaway.scoobergame.domain.model.exceptions.InvalidTurnException;
import com.takeaway.scoobergame.domain.model.valueobjects.Move;
import com.takeaway.scoobergame.interfaces.resources.NextMove;
import com.takeaway.scoobergame.domain.model.valueobjects.PlayerRegistration;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ScooberGameServiceTest {

    @Mock
    private ScooberGame scooberGameMock;

    @InjectMocks
    private ScooberGameService scooberGameService = new ScooberGameService();

    @Test
    public void registerPlayer_ShouldRegisterNewPlayerAndReturnGameIdAndPlayerId() {
        // Prepare test data.
        PlayerRegistration playerRegistrationExcepted = new PlayerRegistration("G1", "P1");

        // Mock external interactions.
        when(scooberGameMock.registerPlayer()).thenReturn(playerRegistrationExcepted);


        // Method under test.
        PlayerRegistration playerRegistrationActual = scooberGameService.registerPlayer();

        // Verifications and assertions.
        verify(scooberGameMock, times(1)).registerPlayer();

        assertThat(playerRegistrationActual, is(notNullValue()));
        assertThat(playerRegistrationActual.getGameId(), is(equalTo(playerRegistrationExcepted.getGameId())));
        assertThat(playerRegistrationActual.getPlayerId(), is(equalTo(playerRegistrationExcepted.getPlayerId())));
    }

    @Test
    public void registerGame_ShouldReturnServerSideEventGivenPlayerRegistrationDetails() {
        // Prepare test data.
        PlayerRegistration playerRegistration = new PlayerRegistration("G1", "P1");
        SseEmitter sseEmitterExpected = new SseEmitter(-1L);

        // Mock external interactions.
        when(scooberGameMock.getEmitterForPlayer(any(PlayerRegistration.class))).thenReturn(sseEmitterExpected);


        // Method under test.
        SseEmitter sseEmitterActual = scooberGameService.registerGame(playerRegistration);

        // Verifications and assertions.
        verify(scooberGameMock, times(1)).getEmitterForPlayer(any(PlayerRegistration.class));

        assertThat(sseEmitterActual, is(notNullValue()));
    }

    @Test
    public void playNextMove_ShouldReturnMoveDetailsGivenNextMoveDetails() throws IOException {
        // Prepare test data.
        NextMove nextMove = new NextMove("G1", "P1", 100);
        Move moveExpected = new Move("P1", 100, 33, -1, false);

        // Mock external interactions.
        when(scooberGameMock.nextMove(any(NextMove.class))).thenReturn(moveExpected);

        // Method under test.
        Move moveActual = scooberGameService.playNextMove(nextMove);

        // Verifications and assertions.
        verify(scooberGameMock, times(1)).nextMove(any(NextMove.class));

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
        NextMove nextMove = new NextMove("G1", "P1", 100);

        // Mock external interactions.
        when(scooberGameMock.nextMove(any(NextMove.class))).thenThrow(new InvalidTurnException());

        // Method under test.
        assertThrows(InvalidTurnException.class, () -> {
            Move moveActual = scooberGameService.playNextMove(nextMove);
        });

        // Verifications and assertions.
        verify(scooberGameMock, times(1)).nextMove(any(NextMove.class));
    }

    @Test
    public void playNextMove_ShouldThrowInvalidNumberException() throws IOException {
        // Prepare test data.
        NextMove nextMove = new NextMove("G1", "P1", 100);

        // Mock external interactions.
        when(scooberGameMock.nextMove(any(NextMove.class))).thenThrow(new InvalidNumberException());

        // Method under test.
        assertThrows(InvalidNumberException.class, () -> {
            Move moveActual = scooberGameService.playNextMove(nextMove);
        });

        // Verifications and assertions.
        verify(scooberGameMock, times(1)).nextMove(any(NextMove.class));
    }

    @Test
    public void playNextMove_ShouldThrowGameAlreadyFinishedException() throws IOException {
        // Prepare test data.
        NextMove nextMove = new NextMove("G1", "P1", 100);

        // Mock external interactions.
        when(scooberGameMock.nextMove(any(NextMove.class))).thenThrow(new GameAlreadyFinishedException());

        // Method under test.
        assertThrows(GameAlreadyFinishedException.class, () -> {
            Move moveActual = scooberGameService.playNextMove(nextMove);
        });

        // Verifications and assertions.
        verify(scooberGameMock, times(1)).nextMove(any(NextMove.class));
    }

    @Test
    public void playNextMove_ShouldThrowGameNotFoundException() throws IOException {
        // Prepare test data.
        NextMove nextMove = new NextMove("G1", "P1", 100);

        // Mock external interactions.
        when(scooberGameMock.nextMove(any(NextMove.class))).thenThrow(new GameNotFoundException());

        // Method under test.
        assertThrows(GameNotFoundException.class, () -> {
            Move moveActual = scooberGameService.playNextMove(nextMove);
        });

        // Verifications and assertions.
        verify(scooberGameMock, times(1)).nextMove(any(NextMove.class));
    }

    @Test
    public void playNextMove_ShouldThrowIOException() throws IOException {
        // Prepare test data.
        NextMove nextMove = new NextMove("G1", "P1", 100);

        // Mock external interactions.
        when(scooberGameMock.nextMove(any(NextMove.class))).thenThrow(new IOException());

        // Method under test.
        assertThrows(IOException.class, () -> {
            Move moveActual = scooberGameService.playNextMove(nextMove);
        });

        // Verifications and assertions.
        verify(scooberGameMock, times(1)).nextMove(any(NextMove.class));
    }
}
