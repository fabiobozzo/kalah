package com.bol.assignment.model.dto;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.util.StringUtils;

@Data
@AllArgsConstructor
@ToString
public class RoomJoinDTO {

  private String id;
  private PlayerCreateDTO player;

  public Boolean isValid() {
    return !StringUtils.isEmpty(id) && !Objects.isNull(player) && player.isValid();
  }
}
