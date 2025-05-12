package org.jkh.planit.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeletePlanRequest {
    @Min(1)
    @NotBlank
    private int scheduleId;

    @Min(1)
    @NotBlank
    private int userId;

    @NotBlank
    private String userPw;
}
