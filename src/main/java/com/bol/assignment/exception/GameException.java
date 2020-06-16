package com.bol.assignment.exception;

public class GameException extends Exception {

  public GameException(String message) {
    super(message);
  }

  public GameException(Throwable cause){
    super(cause);
  }

}
