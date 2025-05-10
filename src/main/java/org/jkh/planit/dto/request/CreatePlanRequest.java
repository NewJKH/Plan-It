package org.jkh.planit.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePlanRequest {
    private int userId;
    private String userPw;
    private String title;
    private String contents;
}
