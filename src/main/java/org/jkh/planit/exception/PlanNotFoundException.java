package org.jkh.planit.exception;

public class PlanNotFoundException extends RuntimeException {
    public PlanNotFoundException(String message) {
        super(message);
    }
   public PlanNotFoundException() {
     super(" 수정할 대상이 없습니다. ");
   }
}
