package org.jkh.planit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class PlanResponse {
    private final int scheduleId;
    private final int userId;
    private String title;
    private String contents;
    private final Date createDate;
    private Date modifyDate;
}
