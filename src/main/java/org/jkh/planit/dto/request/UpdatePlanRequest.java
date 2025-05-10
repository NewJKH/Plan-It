package org.jkh.planit.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdatePlanRequest {
    private int scheduleId;
    private int userId;
    private String userPw;
    private String contents;
}
