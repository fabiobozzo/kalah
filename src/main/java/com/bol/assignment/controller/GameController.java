package com.bol.assignment.controller;

import com.bol.assignment.exception.GameException;
import com.bol.assignment.model.dto.GameDTO;
import com.bol.assignment.model.dto.MoveDto;
import com.bol.assignment.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping("play")
  public void play(@RequestBody MoveDto move) {
    try {
      GameDTO game = gameService.move(move);
      messagingTemplate.convertAndSend("/game/status", game);
    } catch (GameException e) {
      messagingTemplate.convertAndSend("/game/status", GameDTO.builder().errorMessage(e.getMessage()).build());
    }

  }
}
