package com.bol.assignment.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bol.assignment.exception.GameException;
import com.bol.assignment.model.Game;
import com.bol.assignment.model.GamePool;
import com.bol.assignment.model.Player;
import com.bol.assignment.model.PlayerSide;
import com.bol.assignment.model.Room;
import com.bol.assignment.model.RoomStatus;
import com.bol.assignment.model.dto.GameMoveDto;
import com.bol.assignment.model.dto.GameStatusDTO;
import java.util.List;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {

  @InjectMocks
  private GameService testee;

  @Mock
  private RoomService roomService;

  @Mock
  private GamePool gamePool;

  private Room room;

  private static final String ROOM_ID = UUID.randomUUID().toString();
  private static final String P1_ID = UUID.randomUUID().toString();
  private static final String P2_ID = UUID.randomUUID().toString();

  @Before
  public void setUp() {

    room = Room.builder()
        .name("test-room")
        .playerTurn(PlayerSide.P1)
        .status(RoomStatus.BUSY)
        .player1(Player.builder()
            .name("Fabio")
            .email("fabio.bozzo@gmail.com")
            .build())
        .player2(Player.builder()
            .name("Foo Bar")
            .email("foo@bar.com")
            .build())
        .build();

    room.getPlayer1().setId(P1_ID);
    room.getPlayer2().setId(P2_ID);

    when(roomService.findRoomById(anyString())).thenReturn(room);
  }

  @Test
  public void move_switch_turn() throws GameException {

    Game game = mock(Game.class);
    when(game.move(eq(PlayerSide.P1), anyInt())).thenReturn(true);
    when(game.isFinished()).thenReturn(false);
    when(game.getPitsP1()).thenReturn(new int[] {6, 6, 0, 7, 7, 7, 1});
    when(game.getPitsP2()).thenReturn(new int[] {7, 7, 6, 6, 6, 6, 0});
    when(gamePool.findOrCreateGameForRoom(any(Room.class))).thenReturn(game);

    GameStatusDTO status = testee.move(new GameMoveDto(ROOM_ID, P1_ID, 2));

    assertNotNull(status);
    assertNull(status.getWinner());
    assertNull(status.getErrorMessage());
    assertEquals(List.of(6, 6, 0, 7, 7, 7, 1, 7, 7, 6, 6, 6, 6, 0), status.getPits());
    assertEquals(PlayerSide.P2, room.getPlayerTurn());
  }

  @Test
  public void retire() {
  }
}