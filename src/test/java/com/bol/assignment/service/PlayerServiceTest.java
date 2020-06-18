package com.bol.assignment.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bol.assignment.exception.EntityNotFoundException;
import com.bol.assignment.model.Player;
import com.bol.assignment.model.dto.PlayerCreateDTO;
import com.bol.assignment.repo.PlayerRepository;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlayerServiceTest {

  @InjectMocks
  private PlayerService testee;

  @Mock
  private PlayerRepository playerRepository;

  private static final String NAME = "Fabio";
  private static final String EMAIL = "fabio.bozzo@gmail.com";

  @Test
  public void findOrCreate_exists() {

    when(playerRepository.findByNameAndEmail(NAME, EMAIL)).thenReturn(new Player(NAME, EMAIL));
    Player found = testee.findOrCreate(new PlayerCreateDTO(NAME, EMAIL));
    assertNotNull(found);
    assertEquals(NAME, found.getName());
    assertEquals(EMAIL, found.getEmail());
  }

  @Test
  public void findOrCreate_notfound() {
    when(playerRepository.findByNameAndEmail(anyString(), anyString())).thenReturn(null);
    when(playerRepository.save(any(Player.class))).thenReturn(new Player(NAME, EMAIL));
    Player found = testee.findOrCreate(new PlayerCreateDTO(NAME, EMAIL));
    assertNotNull(found);
    assertEquals(NAME, found.getName());
    assertEquals(EMAIL, found.getEmail());
    verify(playerRepository).save(any(Player.class));
  }

  @Test(expected = EntityNotFoundException.class)
  public void findById() {
    when(playerRepository.findById(anyString())).thenReturn(Optional.empty());
    testee.findById("notfound");
  }
}