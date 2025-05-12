package org.jkh.planit.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePlanRequest {

    @NotNull
    @Min(1)
    private int userId;

    @NotBlank
    private String title;

    @NotBlank
    @Size(max = 200, message = "일정은 200자 이내여야 합니다.")
    private String contents;
}
