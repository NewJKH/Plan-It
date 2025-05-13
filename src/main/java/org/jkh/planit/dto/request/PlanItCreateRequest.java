package org.jkh.planit.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 일정 생성 요청을 처리하기 위한 Request DTO입니다.
 *
 * 클라이언트로부터 일정 생성에 필요한 데이터(userId, title, contents)를 전달받으며,
 * 각 필드에 대한 유효성 검증을 통해 잘못된 요청을 사전에 차단합니다.
 *
 * 유효성 검증은 javax.validation 기반으로 처리되며,
 * 컨트롤러 단에서 @Valid 와 함께 사용됩니다.
 */
@Getter
@NoArgsConstructor
public class PlanItCreateRequest {

    /**
     * 일정을 생성할 사용자 식별자입니다.
     *
     * 필수값이며, 1 이상의 값만 허용됩니다.
     * - @NotNull: null 입력 불가
     * - @Min(1): 최소값 1 이상
     */
    @NotNull(message = "userId는 필수입니다.")
    @Min(value = 1, message = "userId는 1 이상이어야 합니다.")
    private int userId;

    /**
     * 일정 제목입니다.
     *
     * 공백 또는 빈 문자열을 허용하지 않으며,
     * 일정 목록이나 상세 화면에서 사용될 핵심 정보입니다.
     * - @NotBlank: null, "", "   " 등 모두 허용하지 않음
     */
    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    /**
     * 일정 본문 또는 설명입니다.
     *
     * 필수 입력이며, 최대 200자까지 허용됩니다.
     * 너무 긴 입력은 데이터베이스 필드 초과 및 UX 저하를 방지하기 위해 제한됩니다.
     * - @NotBlank: 필수값
     * - @Size(max=200): 최대 길이 제한
     */
    @NotBlank(message = "내용은 필수입니다.")
    @Size(max = 200, message = "일정은 200자 이내여야 합니다.")
    private String contents;
}
