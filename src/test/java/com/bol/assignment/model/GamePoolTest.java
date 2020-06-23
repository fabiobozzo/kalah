package com.bol.assignment.model;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GamePoolTest {

  private static final String ROOM_ID = UUID.randomUUID().toString();

  @InjectMocks
  private GamePool testee;

  @Before
  public void setUp() throws NoSuchFieldException {
    FieldSetter.setField(testee, testee.getClass().getDeclaredField("pitsPerPlayer"), 6);
    FieldSetter.setField(testee, testee.getClass().getDeclaredField("stonesPerPit"), 6);
  }

  @Test
  public void findOrCreateGameForRoom_non_existing() throws NoSuchFieldException {

    Map<String, Game> emptyGames = new ConcurrentHashMap<>();
    FieldSetter.setField(testee, testee.getClass().getDeclaredField("games"), emptyGames);

    Room room = new Room();
    room.setId(ROOM_ID);

    Game game = testee.findOrCreateGameForRoom(room);
    assertNotNull(game);
    assertEquals(Integer.valueOf(6), game.getPitsPerPlayer());
    assertEquals(Integer.valueOf(6), game.getStonesPerPit());
    assertEquals(1, emptyGames.size());
    assertTrue(emptyGames.containsKey(ROOM_ID));
  }

  @Test
  public void findOrCreateGameForRoom_existing() throws NoSuchFieldException {

    Map<String, Game> games = new ConcurrentHashMap<>();
    games.put(ROOM_ID, new Game(4, 4));
    FieldSetter.setField(testee, testee.getClass().getDeclaredField("games"), games);

    Room room = new Room();
    room.setId(ROOM_ID);

    Game game = testee.findOrCreateGameForRoom(room);
    assertNotNull(game);
    assertEquals(Integer.valueOf(4), game.getPitsPerPlayer());
    assertEquals(Integer.valueOf(4), game.getStonesPerPit());

    Room anotherRoom = new Room();
    room.setId(UUID.randomUUID().toString());
    Game anotherGame = testee.findOrCreateGameForRoom(room);
    assertNotNull(anotherGame);
    assertEquals(Integer.valueOf(6), anotherGame.getPitsPerPlayer());
    assertEquals(Integer.valueOf(6), anotherGame.getStonesPerPit());

    assertEquals(2, games.size());
    assertTrue(games.containsKey(ROOM_ID));
  }
}