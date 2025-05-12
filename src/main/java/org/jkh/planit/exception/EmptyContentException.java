package org.jkh.planit.exception;

public class EmptyContentException extends RuntimeException {
    public EmptyContentException() {
        super("내용이 비었습니다.");
    }
}
