package com.bol.assignment.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.stream.Stream;
import org.junit.Test;

public class GameTest {

  @Test
  public void move() {
  }

  @Test
  public void dropStones() {
  }

  @Test
  public void applyCaptureRule() {
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