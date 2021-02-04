package com.takeaway.scoobergame.domain.model.aggregates;

import com.takeaway.scoobergame.domain.model.entities.Game;
import com.takeaway.scoobergame.domain.model.entities.Player;
import com.takeaway.scoobergame.domain.model.exceptions.GameAlreadyFinishedException;
import com.takeaway.scoobergame.domain.model.exceptions.GameNotFoundException;
import com.takeaway.scoobergame.domain.model.exceptions.InvalidNumberException;
import com.takeaway.scoobergame.domain.model.exceptions.InvalidTurnException;
import com.takeaway.scoobergame.domain.model.valueobjects.Move;
import com.takeaway.scoobergame.interfaces.resources.NextMove;
import com.takeaway.scoobergame.domain.model.valueobjects.PlayerRegistration;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class ScooberGame {
    private Map<String, Game> games = new Hashtable<>();
    private Game lastGame;

    public synchronized PlayerRegistration registerPlayer() {
        if (lastGame == null) {
            lastGame = new Game(getNewGameId());
            lastGame.setNextMovePlayer(Player.PLAYER_1);
            games.put(lastGame.getGameId(), lastGame);
            return new PlayerRegistration(lastGame.getGameId(), Player.PLAYER_1.getPlayerId());
        } else {
            Game game = lastGame;
            lastGame = null;
            return new PlayerRegistration(game.getGameId(), Player.PLAYER_2.getPlayerId());
        }
    }

    public SseEmitter getEmitterForPlayer(PlayerRegistration playerRegistration) throws GameNotFoundException {
        Game game = games.get(playerRegistration.getGameId());

        validateGame(game);

        if (playerRegistration.getPlayerId().equals(Player.PLAYER_1.getPlayerId()))
            return game.getPlayer1();
        else {
            return game.getPlayer2();
        }
    }

    public Move nextMove(NextMove nextMove)
            throws IOException, InvalidTurnException, InvalidNumberException, GameAlreadyFinishedException, GameNotFoundException {

        Game game = games.get(nextMove.getGameId());

        validateGame(game);

        synchronized (game) {
            validateNextMove(nextMove, game);
            validateNextMoveNumber(nextMove, game);

            Move move = calculateMove(nextMove.getPlayerId(), nextMove.getNumber());
            updateNextTurn(nextMove.getPlayerId(), game, move);

            game.getPlayer1().send(move);
            game.getPlayer2().send(move);

            if (move.isGameFinished()) {
                game.getPlayer1().complete();
                game.getPlayer2().complete();
            }
            return move;
        }
    }

    private void updateNextTurn(String playerId, Game game, Move move) {
        if (playerId.equals(Player.PLAYER_1.getPlayerId())) {
            game.setNextMovePlayer(Player.PLAYER_2);
        } else {
            game.setNextMovePlayer(Player.PLAYER_1);
        }
        game.setLastMove(move);
    }

    private Move calculateMove(String playerId, Integer number) {
        Integer nextNumber;
        Integer added;
        if (number % 3 == 0) {
            nextNumber = number / 3;
            added = 0;
        } else if ((number - 1) % 3 == 0) {
            nextNumber = (number - 1) / 3;
            added = -1;
        } else {
            nextNumber = (number + 1) / 3;
            added = 1;
        }
        return new Move(playerId, number, nextNumber, added, nextNumber == 1 ? true : false);
    }

    private boolean isGameFinished(Game game) {
        return game.getLastMove() != null && game.getLastMove().isGameFinished();
    }

    private void validateNextMoveNumber(NextMove nextMove, Game game) {
        if (game.getLastMove() != null && !nextMove.getNumber().equals(game.getLastMove().getNextNumber())) {
            throw new InvalidNumberException();
        }
    }

    private void validateNextMove(NextMove nextMove, Game game) {
        if (!nextMove.getPlayerId().equals(game.getNextMovePlayer().getPlayerId())) {
            throw new InvalidTurnException();
        }
    }

    private void validateGame(Game game) {
        if (game == null) {
            throw new GameNotFoundException();
        }
        if(isGameFinished(game)) {
            throw new GameAlreadyFinishedException();
        }
    }

    private String getNewGameId() {
        return "G" + (games.size() + 1);
    }
}
