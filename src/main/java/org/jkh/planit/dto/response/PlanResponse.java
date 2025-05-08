package org.jkh.planit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlanResponse {
    private final int scheduleId;
    private final int userId;
    private String title;
    private String contents;
}
