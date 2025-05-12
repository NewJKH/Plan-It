package org.jkh.planit.exception;

public class NotMatchedPasswordException extends RuntimeException {
    public NotMatchedPasswordException(String message) {
        super(message);
    }

    public NotMatchedPasswordException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
