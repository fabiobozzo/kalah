package com.bol.assignment.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.bol.assignment.exception.GameException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

@Slf4j
public class GameTest {

  List<PlayerSide> turns = new ArrayList<>();
  List<Integer> moves = new ArrayList<>();
  List<String> p1States = new ArrayList<>();
  List<String> p2States = new ArrayList<>();

  @Before
  public void setUp() {
    turns.add(PlayerSide.P1); moves.add(4); p2States.add("7,7,7,7,6,6,0"); p1States.add("6,6,6,6,0,7,1");
    turns.add(PlayerSide.P2); moves.add(0); p2States.add("0,8,8,8,7,7,1"); p1States.add("7,6,6,6,0,7,1");
    turns.add(PlayerSide.P1); moves.add(5); p2States.add("1,9,9,9,8,8,1"); p1States.add("7,6,6,6,0,0,2");
    turns.add(PlayerSide.P2); moves.add(2); p2States.add("1,9,0,10,9,9,2"); p1States.add("8,7,7,7,1,0,2");
    turns.add(PlayerSide.P1); moves.add(4); p2States.add("0,9,0,10,9,9,2"); p1States.add("8,7,7,7,0,0,4");
    turns.add(PlayerSide.P2); moves.add(3); p2States.add("0,9,0,0,10,10,5"); p1States.add("9,8,8,8,1,0,4");
    turns.add(PlayerSide.P1); moves.add(0); p2States.add("1,10,1,0,10,10,5"); p1States.add("0,9,9,9,2,1,5");
    turns.add(PlayerSide.P2); moves.add(1); p2States.add("1,0,2,1,11,11,6"); p1States.add("1,10,10,10,3,1,5");
    turns.add(PlayerSide.P1); moves.add(3); p2States.add("2,1,3,2,12,12,6"); p1States.add("2,10,10,0,4,2,6");
    turns.add(PlayerSide.P2); moves.add(4); p2States.add("3,2,4,3,0,13,7"); p1States.add("3,11,11,1,5,3,6");
    turns.add(PlayerSide.P1); moves.add(2); p2States.add("4,3,5,4,1,14,7"); p1States.add("4,11,0,2,6,4,7");
    turns.add(PlayerSide.P2); moves.add(0); p2States.add("0,4,6,5,2,14,7"); p1States.add("4,11,0,2,6,4,7");
    turns.add(PlayerSide.P1); moves.add(3); p2States.add("0,4,6,5,2,14,7"); p1States.add("4,11,0,0,7,5,7");
    turns.add(PlayerSide.P2); moves.add(4); p2States.add("0,4,6,5,0,15,8"); p1States.add("4,11,0,0,7,5,7");
    turns.add(PlayerSide.P2); moves.add(5); p2States.add("1,5,7,6,1,1,10"); p1States.add("6,12,1,1,8,6,7");
    turns.add(PlayerSide.P1); moves.add(1); p2States.add("2,6,8,7,2,2,10"); p1States.add("7,0,2,2,9,7,8");
    turns.add(PlayerSide.P2); moves.add(4); p2States.add("2,6,8,7,0,3,11"); p1States.add("7,0,2,2,9,7,8");
    turns.add(PlayerSide.P2); moves.add(1); p2States.add("2,0,9,8,1,4,12"); p1States.add("8,0,2,2,9,7,8");
    turns.add(PlayerSide.P1); moves.add(4); p2States.add("3,1,10,9,2,5,12"); p1States.add("9,0,2,2,0,8,9");
    turns.add(PlayerSide.P2); moves.add(4); p2States.add("3,1,10,9,0,6,13"); p1States.add("9,0,2,2,0,8,9");
    turns.add(PlayerSide.P2); moves.add(2); p2States.add("3,1,0,10,1,7,14"); p1States.add("10,1,3,3,1,9,9");
    turns.add(PlayerSide.P1); moves.add(3); p2States.add("3,1,0,10,1,7,14"); p1States.add("10,1,3,0,2,10,10");
    turns.add(PlayerSide.P1); moves.add(4); p2States.add("3,1,0,10,1,7,14"); p1States.add("10,1,3,0,0,11,11");
    turns.add(PlayerSide.P1); moves.add(2); p2States.add("3,1,0,10,1,7,14"); p1States.add("10,1,0,1,1,12,11");
    turns.add(PlayerSide.P2); moves.add(1); p2States.add("3,0,0,10,1,7,16"); p1States.add("10,1,0,0,1,12,11");
    turns.add(PlayerSide.P1); moves.add(5); p2States.add("4,1,1,11,2,8,16"); p1States.add("11,2,1,1,2,0,12");
    turns.add(PlayerSide.P2); moves.add(0); p2States.add("0,2,2,12,3,8,16"); p1States.add("11,2,1,1,2,0,12");
    turns.add(PlayerSide.P1); moves.add(4); p2States.add("0,2,2,12,3,8,16"); p1States.add("11,2,1,1,0,1,13");
    turns.add(PlayerSide.P1); moves.add(5); p2States.add("0,2,2,12,3,8,16"); p1States.add("11,2,1,1,0,0,14");
    turns.add(PlayerSide.P1); moves.add(0); p2States.add("1,3,3,13,4,8,16"); p1States.add("0,3,2,2,1,1,15");
    turns.add(PlayerSide.P2); moves.add(3); p2States.add("2,4,4,0,5,9,21"); p1States.add("1,4,0,3,2,2,15");
    turns.add(PlayerSide.P1); moves.add(3); p2States.add("2,4,4,0,5,9,21"); p1States.add("1,4,0,0,3,3,16");
    turns.add(PlayerSide.P1); moves.add(1); p2States.add("2,4,4,0,5,9,21"); p1States.add("1,0,1,1,4,4,16");
    turns.add(PlayerSide.P2); moves.add(2); p2States.add("2,4,0,1,6,10,22"); p1States.add("1,0,1,1,4,4,16");
    turns.add(PlayerSide.P2); moves.add(1); p2States.add("2,0,1,2,7,11,22"); p1States.add("1,0,1,1,4,4,16");
    turns.add(PlayerSide.P1); moves.add(4); p2States.add("3,1,1,2,7,11,22"); p1States.add("1,0,1,1,0,5,17");
    turns.add(PlayerSide.P2); moves.add(5); p2States.add("4,2,2,3,7,0,23"); p1States.add("2,1,2,2,1,6,17");
    turns.add(PlayerSide.P1); moves.add(5); p2States.add("5,3,3,4,8,0,23"); p1States.add("2,1,2,2,1,0,18");
    turns.add(PlayerSide.P2); moves.add(3); p2States.add("5,3,3,0,9,1,24"); p1States.add("3,1,2,2,1,0,18");
    turns.add(PlayerSide.P1); moves.add(3); p2States.add("0,3,3,0,9,1,24"); p1States.add("3,1,2,0,2,0,24");
    turns.add(PlayerSide.P2); moves.add(2); p2States.add("0,3,0,1,10,2,24"); p1States.add("3,1,2,0,2,0,24");
    turns.add(PlayerSide.P1); moves.add(4); p2States.add("0,3,0,1,10,2,24"); p1States.add("3,1,2,0,0,1,25");
    turns.add(PlayerSide.P1); moves.add(5); p2States.add("0,3,0,1,10,2,24"); p1States.add("3,1,2,0,0,0,26");
    turns.add(PlayerSide.P1); moves.add(0); p2States.add("0,3,0,1,10,2,24"); p1States.add("0,2,3,1,0,0,26");
    turns.add(PlayerSide.P2); moves.add(1); p2States.add("0,0,1,2,11,2,24"); p1States.add("0,2,3,1,0,0,26");
    turns.add(PlayerSide.P1); moves.add(2); p2States.add("0,0,1,2,11,2,24"); p1States.add("0,2,0,2,1,1,26");
    turns.add(PlayerSide.P2); moves.add(4); p2States.add("1,1,2,2,0,3,25"); p1States.add("1,3,1,3,2,2,26");
    turns.add(PlayerSide.P1); moves.add(3); p2States.add("1,1,2,2,0,3,25"); p1States.add("1,3,1,0,3,3,27");
    turns.add(PlayerSide.P1); moves.add(4); p2States.add("2,1,2,2,0,3,25"); p1States.add("1,3,1,0,0,4,28");
    turns.add(PlayerSide.P2); moves.add(2); p2States.add("2,1,0,3,0,3,29"); p1States.add("1,0,1,0,0,4,28");
    turns.add(PlayerSide.P1); moves.add(5); p2States.add("3,2,1,3,0,3,29"); p1States.add("1,0,1,0,0,0,29");
    turns.add(PlayerSide.P2); moves.add(3); p2States.add("3,2,1,0,1,4,30"); p1States.add("1,0,1,0,0,0,29");
    turns.add(PlayerSide.P2); moves.add(0); p2States.add("0,3,2,0,1,4,32"); p1States.add("1,0,0,0,0,0,29");
    turns.add(PlayerSide.P1); moves.add(0); p2States.add("0,0,0,0,0,0,41"); p1States.add("0,0,0,0,0,0,31");

  }

  @Test
  public void move() {

    Game game = buildGameWith("6,6,6,6,6,6,0", "6,6,6,6,6,6,0");

    IntStream.range(0, moves.size()).forEach(i -> {
      try {

        boolean lastPosition = game.move(turns.get(i), moves.get(i));
        assertEquals(p1States.get(i), game.printPits(PlayerSide.P1));
        assertEquals(p2States.get(i), game.printPits(PlayerSide.P2));

        log.debug("POS:{} -> {}", i, String.valueOf(lastPosition));

      } catch (GameException e) {
        fail(e.getMessage());
      }
    });

  }


  @Test
  public void isFinished() {
    Game inProgress = buildGameWith("6,6,6,6,6,6,0", "6,6,6,6,6,6,0");
    Game finished = buildGameWith("0,0,0,0,0,0,36", "6,6,6,6,6,6,0");
    assertFalse(inProgress.isFinished());
    assertTrue(finished.isFinished());
  }

  @Test
  public void winner() {
    Game tie = buildGameWith("6,6,6,6,0,0,12", "6,0,0,6,6,6,12");
    Game winner1 = buildGameWith("0,0,0,0,0,0,36", "6,6,6,6,6,6,0");
    Game winner2 = buildGameWith("6,6,6,6,6,6,0", "0,0,0,0,0,0,36");
    assertNull(tie.winner());
    assertEquals(PlayerSide.P1, winner1.winner());
    assertEquals(PlayerSide.P2, winner2.winner());
  }

  private Game buildGameWith(String p1, String p2) {
    return new Game(
        Stream.of(p1.split(",")).mapToInt(Integer::parseInt).toArray(),
        Stream.of(p2.split(",")).mapToInt(Integer::parseInt).toArray(),
        6,
        6
    );
  }
}