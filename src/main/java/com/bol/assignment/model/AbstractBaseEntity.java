package com.bol.assignment.model;

import java.time.LocalDateTime;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
@Data
@EqualsAndHashCode(of = "id")
public abstract class AbstractBaseEntity {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(length = 36)
  @Access(AccessType.PROPERTY)
  protected String id;

  @Column(nullable = false, name = "created_at")
  @CreationTimestamp
  protected LocalDateTime createdAt;

  @Column(nullable = false, name = "updated_at")
  @UpdateTimestamp
  protected LocalDateTime updatedAt;

}
