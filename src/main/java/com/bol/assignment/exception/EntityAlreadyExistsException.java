package com.bol.assignment.exception;

public class EntityAlreadyExistsException extends RuntimeException {

  public EntityAlreadyExistsException(String message) {
    super(message);
  }

  public EntityAlreadyExistsException(Throwable cause){
    super(cause);
  }

}
