package com.bol.assignment.service;

import com.bol.assignment.exception.GameException;
import com.bol.assignment.model.Game;
import com.bol.assignment.model.GamePool;
import com.bol.assignment.model.Player;
import com.bol.assignment.model.PlayerSide;
import com.bol.assignment.model.Room;
import com.bol.assignment.model.RoomStatus;
import com.bol.assignment.model.dto.GameDTO;
import com.bol.assignment.model.dto.MoveDto;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {

  private final RoomService roomService;
  private final GamePool gamePool;

  public GameDTO move(MoveDto move) throws GameException {

    Room room = roomService.findRoomById(move.getRoomId());

    if (
        (room.getPlayerTurn().equals(PlayerSide.P1) && room.getPlayer1().getId().equals(move.getPlayerId())) ||
            (room.getPlayerTurn().equals(PlayerSide.P2) && room.getPlayer2().getId().equals(move.getPlayerId()))
    ) {

      Game game = gamePool.findOrCreateGameForRoom(room);
      boolean switchTurn = game.move(room.getPlayerTurn(), move.getPosition());

      if (game.isFinished()) {
        PlayerSide winner = game.winner();
        room.setStatus(RoomStatus.CLOSED);
        room.setWinner(winner.equals(PlayerSide.P1) ? room.getPlayer1() : room.getPlayer2());

      } else if (switchTurn) {
        room.setPlayerTurn(room.getPlayerTurn().equals(PlayerSide.P1) ? PlayerSide.P2 : PlayerSide.P1);
      }

      return GameDTO.builder()
          .pits(IntStream.concat(Arrays.stream(game.getPitsP1()), Arrays.stream(game.getPitsP2()))
              .boxed()
              .collect(Collectors.toList()))
          .winner(Optional.ofNullable(room.getWinner()).map(Player::getName).orElse(null))
          .build();

    } else {
      throw new GameException("It's not your turn!");
    }
  }
}
