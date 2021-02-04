package com.takeaway.scoobergame.interfaces.rest;

import com.takeaway.scoobergame.application.ScooberGameService;
import com.takeaway.scoobergame.domain.model.exceptions.GameAlreadyFinishedException;
import com.takeaway.scoobergame.domain.model.exceptions.GameNotFoundException;
import com.takeaway.scoobergame.domain.model.exceptions.InvalidNumberException;
import com.takeaway.scoobergame.domain.model.exceptions.InvalidTurnException;
import com.takeaway.scoobergame.domain.model.valueobjects.PlayerRegistration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerAdviserTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScooberGameService scooberGameServiceMock;

    @Test
    public void shouldThrowGameNotFoundException() throws Exception {
        // Mock external interactions.
        when(scooberGameServiceMock.registerGame(any(PlayerRegistration.class))).thenThrow(new GameNotFoundException());

        // API under test.
        this.mockMvc.perform(get("/game/registergame/G1/P1")).andExpect(status().isNotFound());
    }

    @Test
    public void shouldThrowInvalidTurnException() throws Exception {
        // Mock external interactions.
        when(scooberGameServiceMock.registerGame(any(PlayerRegistration.class))).thenThrow(new InvalidTurnException());

        // API under test.
        this.mockMvc.perform(get("/game/registergame/G1/P1")).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldThrowInvalidNumberException() throws Exception {
        // Mock external interactions.
        when(scooberGameServiceMock.registerGame(any(PlayerRegistration.class))).thenThrow(new InvalidNumberException());

        // API under test.
        this.mockMvc.perform(get("/game/registergame/G1/P1")).andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldThrowGameAlreadyFinishedException() throws Exception {
        // Mock external interactions.
        when(scooberGameServiceMock.registerGame(any(PlayerRegistration.class))).thenThrow(new GameAlreadyFinishedException());

        // API under test.
        this.mockMvc.perform(get("/game/registergame/G1/P1")).andExpect(status().isBadRequest());
    }
}
