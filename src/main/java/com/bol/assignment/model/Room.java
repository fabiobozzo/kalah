package com.bol.assignment.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@Builder
@ToString
@EqualsAndHashCode(callSuper = true)
public class Room extends AbstractBaseEntity {

  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "player1_id")
  private Player player1;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "player2_id")
  private Player player2;

  @Enumerated(EnumType.STRING)
  private RoomStatus status = RoomStatus.OPEN;

  @Enumerated(EnumType.STRING)
  private PlayerSide playerTurn = PlayerSide.P1;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "player_winner_id")
  private Player winner;
}
