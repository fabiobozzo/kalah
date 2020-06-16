package com.bol.assignment.exception;

import java.util.function.Supplier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException(String message) {
    super(message);
  }

  public EntityNotFoundException(
      String type,
      String identifierType,
      Object identifier
  ) {
    this("Can't find " + type + " with " + identifierType + ": " + identifier);
  }

  public static Supplier<EntityNotFoundException> of(
      String type,
      String identifierType,
      Object identifier
  ) {
    return () -> new EntityNotFoundException(type, identifierType, identifier);
  }

}
