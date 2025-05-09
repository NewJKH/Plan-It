package org.jkh.planit.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

  public UserNotFoundException() {
      super(" 존재하지 않는 유저 입니다. ");
  }
}
