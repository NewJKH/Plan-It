package org.jkh.planit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jkh.planit.entity.Plan;

@Getter
@AllArgsConstructor
public class PlanItResponse {
    private final int scheduleId;
    private final int userId;
    private String title;
    private String contents;
    public static PlanItResponse toDto(Plan plan){
        return new PlanItResponse(plan.getScheduleId(),plan.getUserId(),plan.getTitle(),plan.getContents());
    }
}
