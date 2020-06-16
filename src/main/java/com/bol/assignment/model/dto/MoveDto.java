package com.bol.assignment.model.dto;

import lombok.Data;

@Data
public class MoveDto {

  private String roomId;
  private String playerId;
  private Integer position;
}
