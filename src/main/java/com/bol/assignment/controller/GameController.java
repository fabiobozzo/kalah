package com.bol.assignment.controller;

import com.bol.assignment.exception.GameException;
import com.bol.assignment.model.dto.GameMoveDto;
import com.bol.assignment.model.dto.GameRetireDto;
import com.bol.assignment.model.dto.GameStatusDTO;
import com.bol.assignment.service.GameService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class GameController {

  private final SimpMessagingTemplate messagingTemplate;
  private final GameService gameService;

  @Value("${stomp.destination}")
  private String stompDestination;

  @PostMapping("play")
  public void play(@RequestBody GameMoveDto move) {

    log.debug(move.toString());

    GameStatusDTO game = null;
    try {
      game = gameService.move(move);
    } catch (GameException e) {
      game = GameStatusDTO.builder().errorMessage(e.getMessage()).build();
    } finally {
      sendGameStatus(game);
    }
  }
  
  @PostMapping("retire")
  public void retire(@RequestBody GameRetireDto retire) {
    GameStatusDTO game = null;
    try {
      game = gameService.retire(retire);
    } catch (GameException e) {
      game = GameStatusDTO.builder().errorMessage(e.getMessage()).build();
    } finally {
      sendGameStatus(game);
    }
  }

  private void sendGameStatus(GameStatusDTO game) {
    Optional.ofNullable(game).ifPresent(g -> messagingTemplate.convertAndSend(stompDestination, g));
  }

}
