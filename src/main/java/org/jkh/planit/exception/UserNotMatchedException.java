package org.jkh.planit.exception;

public class UserNotMatchedException extends RuntimeException {
    public UserNotMatchedException(String message) {
        super(message);
    }

    public UserNotMatchedException() {
        super(" 사용자가 일치하지 않습니다. ");
    }
}
