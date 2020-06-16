package com.bol.assignment.model.dto;

import javax.validation.constraints.Email;
import lombok.Data;
import lombok.ToString;
import org.springframework.util.StringUtils;

@Data
@ToString
public class PlayerCreateDTO {

  private String name;

  @Email
  private String email;

  public Boolean isValid() {
    return !StringUtils.isEmpty(name) && !StringUtils.isEmpty(email);
  }
}
