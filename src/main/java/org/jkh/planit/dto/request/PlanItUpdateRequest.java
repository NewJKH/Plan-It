package org.jkh.planit.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 일정 수정 요청을 처리하기 위한 Request DTO입니다.
 *
 * 클라이언트는 일정 ID, 사용자 인증 정보, 수정할 내용을 포함한 요청을 전송합니다.
 * 일부 필드(contents)는 선택적 수정이 가능하도록 설계되었으며,
 * 유효성 검증을 통해 비정상 요청을 사전에 차단합니다.
 */
@Getter
@NoArgsConstructor
public class PlanItUpdateRequest {

    /**
     * 수정 대상 일정의 고유 식별자입니다.
     *
     * 필수값이며, 1 이상의 값만 허용됩니다.
     * - @NotNull: null 방지
     * - @Min(1): 0 이하의 값은 허용하지 않음
     */
    @NotNull(message = "scheduleId는 필수입니다.")
    @Min(value = 1, message = "scheduleId는 1 이상이어야 합니다.")
    private int scheduleId;

    /**
     * 요청을 수행하는 사용자의 고유 식별자입니다.
     *
     * 필수값이며, 1 이상의 값만 허용됩니다.
     * 수정 권한 검증 등에 사용됩니다.
     */
    @NotNull(message = "userId는 필수입니다.")
    @Min(value = 1, message = "userId는 1 이상이어야 합니다.")
    private int userId;

    /**
     * 요청을 수행하는 사용자의 비밀번호입니다.
     *
     * 일정 수정이 인증된 사용자만 가능하도록 하기 위한 본인 확인 수단입니다.
     * - @NotBlank: null, "", 공백 문자열 모두 허용하지 않음
     */
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String userPw;

    /**
     * 수정할 일정의 내용입니다.
     *
     * 선택 입력이며, 200자 이하만 허용됩니다.
     * 값이 null 이어도 무방하지만, 값이 들어왔다면 길이 제약이 적용됩니다.
     * - @Size: 최대 길이 제한
     */
    @Size(max = 200, message = "일정은 200자 이내여야 합니다.")
    private String contents;
}
