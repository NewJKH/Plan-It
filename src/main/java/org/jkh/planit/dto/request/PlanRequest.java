package org.jkh.planit.dto.request;

import lombok.Getter;

@Getter
public class PlanRequest {
    private int userId;
    private String userPw;
    private String title;
    private String contents;
}
