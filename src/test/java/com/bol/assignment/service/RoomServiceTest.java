package com.bol.assignment.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bol.assignment.exception.EntityAlreadyExistsException;
import com.bol.assignment.exception.EntityNotFoundException;
import com.bol.assignment.exception.ValidationException;
import com.bol.assignment.model.Player;
import com.bol.assignment.model.Room;
import com.bol.assignment.model.RoomStatus;
import com.bol.assignment.model.dto.PlayerCreateDTO;
import com.bol.assignment.model.dto.RoomCreateDTO;
import com.bol.assignment.model.dto.RoomJoinDTO;
import com.bol.assignment.repo.RoomRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RoomServiceTest {

  private static final String ROOM_ID = UUID.randomUUID().toString();
  private static final String ROOM_NAME = "room-name";
  private static final String PLAYER1_NAME = "player1-name";
  private static final String PLAYER1_EMAIL = "player1@bol.com";
  private static final String PLAYER2_NAME = "player2-name";
  private static final String PLAYER2_EMAIL = "player2@bol.com";

  @InjectMocks
  private RoomService testee;

  @Mock
  private RoomRepository roomRepository;

  @Mock
  private PlayerService playerService;

  @Test
  public void createRoom() {

    when(roomRepository.existsByNameAndStatus(anyString(), eq(RoomStatus.OPEN))).thenReturn(false);
    when(playerService.findOrCreate(any(PlayerCreateDTO.class))).thenReturn(Player.builder()
        .name(PLAYER1_NAME)
        .email(PLAYER1_EMAIL)
        .build());

    RoomCreateDTO roomToBeCreated = new RoomCreateDTO(ROOM_NAME, new PlayerCreateDTO(PLAYER1_NAME, PLAYER1_EMAIL));
    testee.createRoom(roomToBeCreated);

    ArgumentCaptor<Room> captor = ArgumentCaptor.forClass(Room.class);
    verify(roomRepository).save(captor.capture());

    Room savedRoom = captor.getValue();
    assertNotNull(savedRoom);
    assertEquals(ROOM_NAME, savedRoom.getName());
    assertEquals(PLAYER1_NAME, savedRoom.getPlayer1().getName());
    assertEquals(PLAYER1_EMAIL, savedRoom.getPlayer1().getEmail());
  }

  @Test(expected = ValidationException.class)
  public void createRoom_invalid() {
    RoomCreateDTO roomToBeCreated = new RoomCreateDTO("", null);
    testee.createRoom(roomToBeCreated);
  }

  @Test(expected = EntityAlreadyExistsException.class)
  public void createRoom_existing_room() {
    when(roomRepository.existsByNameAndStatus(anyString(), eq(RoomStatus.OPEN))).thenReturn(true);
    RoomCreateDTO roomToBeCreated = new RoomCreateDTO(ROOM_NAME, new PlayerCreateDTO(PLAYER1_NAME, PLAYER1_EMAIL));
    testee.createRoom(roomToBeCreated);
  }

  @Test
  public void joinRoom() {

    Room room = Room.builder()
        .status(RoomStatus.OPEN)
        .player1(Player.builder()
            .name(PLAYER1_NAME)
            .email(PLAYER1_EMAIL)
            .build())
        .build();

    when(roomRepository.findById(ROOM_ID)).thenReturn(Optional.of(room));
    when(playerService.findOrCreate(any(PlayerCreateDTO.class))).thenReturn(Player.builder()
        .name(PLAYER2_NAME)
        .email(PLAYER2_EMAIL)
        .build());

    testee.joinRoom(new RoomJoinDTO(ROOM_ID, new PlayerCreateDTO(PLAYER2_NAME, PLAYER2_EMAIL)));

    ArgumentCaptor<Room> captor = ArgumentCaptor.forClass(Room.class);
    verify(roomRepository).save(captor.capture());

    Room savedRoom = captor.getValue();
    assertNotNull(savedRoom);
    assertEquals(PLAYER2_NAME, savedRoom.getPlayer2().getName());
    assertEquals(PLAYER2_EMAIL, savedRoom.getPlayer2().getEmail());
    assertEquals(RoomStatus.BUSY, savedRoom.getStatus());
  }

  @Test(expected = EntityNotFoundException.class)
  public void findRoomById() {
    when(roomRepository.findById(anyString())).thenReturn(Optional.empty());
    testee.findRoomById(UUID.randomUUID().toString());
  }

}