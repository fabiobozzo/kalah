package com.bol.assignment.service;

import com.bol.assignment.exception.EntityNotFoundException;
import com.bol.assignment.model.Player;
import com.bol.assignment.model.dto.PlayerCreateDTO;
import com.bol.assignment.repo.PlayerRepository;
import java.util.Objects;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerService {

  private final PlayerRepository playerRepository;

  @Transactional
  public Player findOrCreate(PlayerCreateDTO player) {
    Player playerFound;
    if (!Objects.isNull(playerFound = playerRepository.findByNameAndEmail(player.getName(), player.getEmail()))) {
      return playerFound;
    } else {
      return playerRepository.save(Player.builder()
          .name(player.getName())
          .email(player.getEmail())
          .build());
    }
  }

  public Player findById(String id) {
    return playerRepository.findById(id)
        .orElseThrow(EntityNotFoundException.of("Player", "id", id));
  }
}
