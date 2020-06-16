package com.bol.assignment.model.dto;

import lombok.Data;
import lombok.ToString;
import org.springframework.util.StringUtils;

@Data
@ToString
public class RoomCloseDTO {

  private String roomId;
  private String playerId;

  public Boolean isValid() {
    return !StringUtils.isEmpty(roomId) && !StringUtils.isEmpty(roomId);
  }
}
