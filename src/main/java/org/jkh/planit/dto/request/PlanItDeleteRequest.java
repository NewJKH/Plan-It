package org.jkh.planit.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 일정 삭제 요청을 처리하기 위한 Request DTO입니다.
 *
 * 클라이언트는 일정 ID, 사용자 ID, 사용자 비밀번호를 함께 전송해야 하며,
 * 해당 요청은 서버에서 인증 및 권한 검사를 거친 후 삭제가 수행됩니다.
 *
 * 컨트롤러에서 @Valid 와 함께 사용됩니다.
 */
@Getter
@NoArgsConstructor
public class PlanItDeleteRequest {

    /**
     * 삭제 대상 일정의 고유 식별자입니다.
     *
     * 필수값이며, 1 이상의 값이어야 합니다.
     * - @Min(1): 최소 1 이상
     */
    @Min(value = 1, message = "scheduleId는 1 이상이어야 합니다.")
    private int scheduleId;

    /**
     * 요청을 수행하는 사용자의 고유 식별자입니다.
     *
     * 필수값이며, 1 이상의 값이어야 합니다.
     */
    @Min(value = 1, message = "userId는 1 이상이어야 합니다.")
    private int userId;

    /**
     * 사용자의 비밀번호입니다.
     *
     * 일정 삭제 요청이 민감한 작업이므로,
     * 추가적인 사용자 본인 확인을 위해 비밀번호를 함께 전달받습니다.
     * - @NotBlank: null, 빈 문자열, 공백 모두 허용하지 않음
     */
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String userPw;
}

