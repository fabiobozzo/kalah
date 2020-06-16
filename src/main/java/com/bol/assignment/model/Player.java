package com.bol.assignment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@Builder
@ToString
@EqualsAndHashCode(callSuper = true)
public class Player extends AbstractBaseEntity {

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique = true)
  private String email;

}
