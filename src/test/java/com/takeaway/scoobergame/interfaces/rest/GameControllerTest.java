package com.takeaway.scoobergame.interfaces.rest;

import com.takeaway.scoobergame.application.ScooberGameService;
import com.takeaway.scoobergame.domain.model.exceptions.GameNotFoundException;
import com.takeaway.scoobergame.domain.model.valueobjects.Move;
import com.takeaway.scoobergame.domain.model.valueobjects.PlayerRegistration;
import com.takeaway.scoobergame.interfaces.resources.NextMove;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScooberGameService scooberGameServiceMock;

    @Test
    public void shouldRegisterNewPlayerAndReturnPlayerRegistrationDetails() throws Exception {
        // Prepare test data.
        String expected = "{gameId: G1, playerId: P1}";

        // Mock external interactions.
        when(scooberGameServiceMock.registerPlayer()).thenReturn(new PlayerRegistration("G1", "P1"));

        // API under test.
        MvcResult mvcResult = this.mockMvc.perform(post("/game/registerplayer"))
                .andExpect(status().isOk()).andReturn();

        // Assertions.
        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    public void shouldRegisterGameAndReturnSseEmitterForPlayer() throws Exception {
        // Mock external interactions.
        when(scooberGameServiceMock.registerGame(any(PlayerRegistration.class))).thenReturn(new SseEmitter());

        // API under test.
        this.mockMvc.perform(get("/game/registergame/G1/P1")).andExpect(status().isOk());
    }

    @Test
    public void shouldPlayNextMoveAndReturnMoveDetails() throws Exception {
        // Prepare test data.
        String nextMove = "{\"gameId\": \"G1\", \"playerId\": \"P1\", \"number\": 100}";
        Move moveExpected = new Move("P1", 100, 33, -1, false);
        String expectedResponse = "{player: P1, number: 100, nextNumber: 33, added: -1, gameFinished: false}";

        // Mock external interactions.
        when(scooberGameServiceMock.playNextMove(any(NextMove.class))).thenReturn(moveExpected);

        // API under test.
        MvcResult mvcResult = this.mockMvc.perform(post("/game/playNextMove").contentType("application/json").content(nextMove))
                .andExpect(status().isOk()).andReturn();

        // Assertions.
        JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), false);
    }
}
