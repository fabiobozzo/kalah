package com.bol.assignment.model;

import com.bol.assignment.exception.GameException;
import java.util.Arrays;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
public class Game {

  private int[] pitsP1;
  private int[] pitsP2;

  private Integer pitsPerPlayer;
  private Integer stonesPerPit;

  public Game(Integer pitsPerPlayer, Integer stonesPerPit) {

    this.pitsPerPlayer = pitsPerPlayer;
    this.stonesPerPit = stonesPerPit;

    pitsP1 = new int[pitsPerPlayer + 1];
    pitsP2 = new int[pitsPerPlayer + 1];

    Arrays.fill(pitsP1, stonesPerPit);
    Arrays.fill(pitsP2, stonesPerPit);

    pitsP1[pitsPerPlayer] = 0;
    pitsP2[pitsPerPlayer] = 0;
  }

  public synchronized boolean move(PlayerSide playerSide, Integer position) throws GameException {

    if (position < 0 || position > pitsPerPlayer - 1) {
      throw new GameException("Position out of bounds: " + position);
    }

    int lastPosition;
    int[] ownPit;
    int[] opponentPit;

    if (playerSide.equals(PlayerSide.P1)) {
      ownPit = pitsP1;
      opponentPit = pitsP2;

    } else if (playerSide.equals(PlayerSide.P2)) {
      ownPit = pitsP2;
      opponentPit = pitsP1;

    } else {
      throw new GameException("Invalid PlayerSide: " + playerSide);
    }

    lastPosition = dropStones(ownPit, opponentPit, position);

    applyCaptureRule(lastPosition, ownPit, opponentPit);

    return lastPosition != pitsPerPlayer;
  }

  public synchronized int dropStones(int[] ownPit, int[] opponentPit, Integer position) throws GameException {

    int[] currentPit = ownPit;
    Integer stonesInPit = currentPit[position];
    if (stonesInPit == 0) {
      throw new GameException("An empty pit has been selected. Null move.");
    }

    currentPit[position] = 0;
    int lastPosition = position++;

    while (stonesInPit > 0) {

      currentPit[position]++;
      lastPosition = position;

      if (currentPit == ownPit && position == currentPit.length - 1) {
        currentPit = opponentPit;
        position = 0;
      } else if (currentPit == opponentPit && position == currentPit.length - 2) {
        currentPit = ownPit;
        position = 0;
      } else {
        position++;
      }

      stonesInPit--;
    }

    return lastPosition;
  }

  public synchronized void applyCaptureRule(int lastPosition, int[] ownPit, int[] opponentPit) {
    if (ownPit[lastPosition] == 1) {
      int oppositePosition = ownPit.length - 2 - lastPosition;
      ownPit[ownPit.length - 1] += 1 + opponentPit[oppositePosition];
      ownPit[lastPosition] = 0;
      opponentPit[oppositePosition] = 0;
    }
  }

  public synchronized boolean isFinished() {
    return IntStream.of(Arrays.copyOfRange(pitsP1, 0, pitsP1.length - 1)).sum() == 0 ||
        IntStream.of(Arrays.copyOfRange(pitsP2, 0, pitsP2.length - 1)).sum() == 0;
  }

  public synchronized PlayerSide winner() {
    if (pitsP1[pitsP1.length - 1] > pitsP2[pitsP2.length - 1]) {
      return PlayerSide.P1;
    } else if (pitsP2[pitsP2.length - 1] > pitsP1[pitsP1.length - 1]) {
      return PlayerSide.P2;
    } else {
      return null;
    }
  }

}
