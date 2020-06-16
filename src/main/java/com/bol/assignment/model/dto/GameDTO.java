package com.bol.assignment.model.dto;

import java.util.List;
import javax.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.util.StringUtils;

@Data
@Builder
@ToString
public class GameDTO {

  private final List<Integer> pits;
  private final String winner;

  private final String errorMessage;
}
