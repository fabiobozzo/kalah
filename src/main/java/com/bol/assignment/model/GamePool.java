package com.bol.assignment.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GamePool {

  @Value("${board.pitsPerPlayer}")
  private Integer pitsPerPlayer;

  @Value("${board.stonesPerPit}")
  private Integer stonesPerPit;

  private Map<String, Game> games = new ConcurrentHashMap<>();

  public Game findOrCreateGameForRoom(Room room) {
    if (games.containsKey(room.getId())) {
      return games.get(room.getId());
    }
    Game game = new Game(pitsPerPlayer, stonesPerPit);
    games.put(room.getId(), game);
    return game;
  }
}
