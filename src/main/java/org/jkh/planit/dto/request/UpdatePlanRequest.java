package org.jkh.planit.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdatePlanRequest {
    @NotNull
    @Min(1)
    private int scheduleId;

    @NotNull
    @Min(1)
    private int userId;

    @NotBlank
    private String userPw;
    private String contents;
}
