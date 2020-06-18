package com.bol.assignment.model.dto;

import lombok.Data;

@Data
public class GameMoveDto {

  private String roomId;
  private String playerId;
  private Integer position;
}
