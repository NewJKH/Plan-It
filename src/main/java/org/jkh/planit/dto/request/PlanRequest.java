package org.jkh.planit.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlanRequest {
    private int scheduleId;
    private int userId;
    private String userPw;
    private String title;
    private String contents;
}
