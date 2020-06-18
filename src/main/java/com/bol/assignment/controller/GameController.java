package com.bol.assignment.controller;

import com.bol.assignment.exception.GameException;
import com.bol.assignment.model.dto.GameMoveDto;
import com.bol.assignment.model.dto.GameRetireDto;
import com.bol.assignment.model.dto.GameStatusDTO;
import com.bol.assignment.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

  @PostMapping("play")
  public void play(@RequestBody GameMoveDto move) {
    try {
      GameStatusDTO game = gameService.move(move);
      messagingTemplate.convertAndSend("/game/status", game);
    } catch (GameException e) {
      messagingTemplate.convertAndSend("/game/status", GameStatusDTO.builder().errorMessage(e.getMessage()).build());
    }
  }

  @PostMapping("retire")
  public void retire(@RequestBody GameRetireDto retire) {
    try {
      GameStatusDTO game = gameService.retire(retire);
      messagingTemplate.convertAndSend("/game/status", game);
    } catch (GameException e) {
      messagingTemplate.convertAndSend("/game/status", GameStatusDTO.builder().errorMessage(e.getMessage()).build());
    }
  }

}
