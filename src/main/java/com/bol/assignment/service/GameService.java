package com.bol.assignment.service;

import com.bol.assignment.exception.GameException;
import com.bol.assignment.model.Game;
import com.bol.assignment.model.GamePool;
import com.bol.assignment.model.Player;
import com.bol.assignment.model.PlayerSide;
import com.bol.assignment.model.Room;
import com.bol.assignment.model.RoomStatus;
import com.bol.assignment.model.dto.GameMoveDto;
import com.bol.assignment.model.dto.GameRetireDto;
import com.bol.assignment.model.dto.GameStatusDTO;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {

  private final RoomService roomService;
  private final GamePool gamePool;

  @Transactional
  public GameStatusDTO move(GameMoveDto move) throws GameException {

    Room room = roomService.findRoomById(move.getRoomId());

    if (isPlayerInRoom(move.getPlayerId(), room) && room.getStatus().equals(RoomStatus.BUSY)) {

      Game game = gamePool.findOrCreateGameForRoom(room);
      boolean switchTurn = game.move(room.getPlayerTurn(), move.getPosition());

      if (game.isFinished()) {
        PlayerSide winner = game.winner();
        room.setStatus(RoomStatus.CLOSED);
        room.setWinner(winner.equals(PlayerSide.P1) ? room.getPlayer1() : room.getPlayer2());

      } else if (switchTurn) {
        room.setPlayerTurn(room.getPlayerTurn().equals(PlayerSide.P1) ? PlayerSide.P2 : PlayerSide.P1);
      }

      return buildGameStatusWith(room, game);

    } else {
      throw new GameException("Cannot perform this move right now! It's not your turn or the game is over.");
    }
  }

  @Transactional
  public GameStatusDTO retire(GameRetireDto retire) throws GameException {

    Room room = roomService.findRoomById(retire.getRoomId());

    if (room.getStatus().equals(RoomStatus.BUSY)) {
      Game game = gamePool.findOrCreateGameForRoom(room);
      room.setStatus(RoomStatus.CLOSED);
      room.setWinner(room.getPlayerOpponentById(retire.getPlayerId()));
      return buildGameStatusWith(room, game);
    } else {
      throw new GameException("Cannot retire from this game.");
    }
  }

  private GameStatusDTO buildGameStatusWith(Room room, Game game) {
    return GameStatusDTO.builder()
        .pits(IntStream.concat(Arrays.stream(game.getPitsP1()), Arrays.stream(game.getPitsP2()))
            .boxed()
            .collect(Collectors.toList()))
        .winner(Optional.ofNullable(room.getWinner()).map(Player::getName).orElse(null))
        .build();
  }

  private boolean isPlayerInRoom(String playerId, Room room) {
    return (room.getPlayerTurn().equals(PlayerSide.P1) && room.getPlayer1().getId().equals(playerId)) ||
        (room.getPlayerTurn().equals(PlayerSide.P2) && room.getPlayer2().getId().equals(playerId));
  }
}
