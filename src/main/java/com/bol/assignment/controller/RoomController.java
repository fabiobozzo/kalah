package com.bol.assignment.controller;

import com.bol.assignment.model.Room;
import com.bol.assignment.model.dto.GameStatusDTO;
import com.bol.assignment.model.dto.RoomCreateDTO;
import com.bol.assignment.model.dto.RoomJoinDTO;
import com.bol.assignment.service.GameService;
import com.bol.assignment.service.RoomService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class RoomController {

  private final SimpMessagingTemplate messagingTemplate;
  private final RoomService roomService;
  private final GameService gameService;

  @Value("${stomp.destination}")
  private String stompDestination;

  @GetMapping("room")
  public List<Room> openRooms() {
    return roomService.findOpenRooms();
  }

  @GetMapping("leaderboard")
  public List<Room> closedRooms() {
    return roomService.findClosedRooms();
  }

  @PostMapping("room")
  public Room createRoom(@RequestBody RoomCreateDTO room) {
    return roomService.createRoom(room);
  }

  @PutMapping("room")
  public Room joinRoom(@RequestBody RoomJoinDTO room) {
    sendGameStatus(gameService.getStatus(room.getId()));
    return roomService.joinRoom(room);
  }

  private void sendGameStatus(GameStatusDTO game) {
    Optional.ofNullable(game).ifPresent(g -> messagingTemplate.convertAndSend(stompDestination, g));
  }
}
