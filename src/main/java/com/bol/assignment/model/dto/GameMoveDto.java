package com.bol.assignment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class GameMoveDto {

  private String roomId;
  private String playerId;
  private Integer position;
}
