package com.bol.assignment.model.dto;

import java.util.Objects;
import lombok.Data;
import lombok.ToString;
import org.springframework.util.StringUtils;

@Data
@ToString
public class RoomCreateDTO {

  private String name;
  private PlayerCreateDTO player;

  public Boolean isValid() {
    return !StringUtils.isEmpty(name) && !Objects.isNull(player) && player.isValid();
  }
}
