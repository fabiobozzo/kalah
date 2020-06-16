package com.bol.assignment.service;

import com.bol.assignment.exception.EntityAlreadyExistsException;
import com.bol.assignment.exception.EntityNotFoundException;
import com.bol.assignment.exception.ValidationException;
import com.bol.assignment.model.Room;
import com.bol.assignment.model.RoomStatus;
import com.bol.assignment.model.dto.RoomCloseDTO;
import com.bol.assignment.model.dto.RoomCreateDTO;
import com.bol.assignment.model.dto.RoomJoinDTO;
import com.bol.assignment.repo.RoomRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

  private final RoomRepository roomRepository;
  private final PlayerService playerService;

  @Transactional
  public Room createRoom(RoomCreateDTO room) {

    validate(room);

    return roomRepository.save(Room.builder()
        .name(room.getName())
        .player1(playerService.findOrCreate(room.getPlayer()))
        .build());
  }

  @Transactional
  public Room joinRoom(RoomJoinDTO roomJoin) {

    validate(roomJoin);

    Room room = findRoomById(roomJoin.getId());
    room.setPlayer2(playerService.findOrCreate(roomJoin.getPlayer()));
    room.setStatus(RoomStatus.BUSY);

    return roomRepository.save(room);
  }

  @Transactional
  public void closeRoom(RoomCloseDTO roomClose) {

    validate(roomClose);

    Room room = findRoomById(roomClose.getRoomId());
    room.setStatus(RoomStatus.CLOSED);
    room.setWinner(playerService.findById(roomClose.getPlayerId()));

    roomRepository.save(room);
  }

  public Room findRoomById(String roomId) {
    return roomRepository.findById(roomId)
        .orElseThrow(EntityNotFoundException.of("Room", "id", roomId));
  }

  public List<Room> findOpenRooms() {
    return roomRepository.findByStatusOrderByCreatedAtDesc(RoomStatus.OPEN);
  }

  public List<Room> findClosedRooms() {
    return roomRepository.findByStatusOrderByCreatedAtDesc(RoomStatus.CLOSED);
  }

  //

  private void validate(RoomCreateDTO room) {
    if (!room.isValid()) {
      throw new ValidationException("Invalid Room:" + room.toString());
    }
    if (roomRepository.existsByNameAndStatus(room.getName(), RoomStatus.OPEN)) {
      throw new EntityAlreadyExistsException("Room:" + room.toString());
    }
  }

  private void validate(RoomJoinDTO roomJoin) {
    if (!roomJoin.isValid()) {
      throw new ValidationException("Invalid Room:" + roomJoin.toString());
    }
  }

  private void validate(RoomCloseDTO roomClose) {
    if (!roomClose.isValid()) {
      throw new ValidationException("Invalid Room:" + roomClose.toString());
    }
  }
}
