package com.bol.assignment.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Room extends AbstractBaseEntity {

  private String name;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "player1_id")
  private Player player1;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "player2_id")
  private Player player2;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  private RoomStatus status = RoomStatus.OPEN;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  private PlayerSide playerTurn = PlayerSide.P1;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "player_winner_id")
  private Player winner;

  public Player getPlayerById(String playerId) {
    if (playerId.equals(player1.getId())) {
      return player1;
    }
    if (playerId.equals(player2.getId())) {
      return player2;
    }
    return null;
  }

  public Player getPlayerOpponentById(String playerId) {
    if (playerId.equals(player1.getId())) {
      return player2;
    }
    if (playerId.equals(player2.getId())) {
      return player1;
    }
    return null;
  }

}
