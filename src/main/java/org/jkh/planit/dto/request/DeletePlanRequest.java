package org.jkh.planit.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeletePlanRequest {
    private int scheduleId;
    private int userId;
    private String userPw;
}
