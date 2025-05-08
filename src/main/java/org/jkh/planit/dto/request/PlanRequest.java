package org.jkh.planit.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class PlanRequest {
    private final int userId;
    private final String userPw;
    private String title;
    private String contents;
    private Date date;
}
