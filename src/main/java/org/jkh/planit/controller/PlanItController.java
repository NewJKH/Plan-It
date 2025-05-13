package org.jkh.planit.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jkh.planit.dto.request.PlanItCreateRequest;
import org.jkh.planit.dto.request.PlanItDeleteRequest;
import org.jkh.planit.dto.request.PlanItUpdateRequest;
import org.jkh.planit.dto.response.PlanItResponse;
import org.jkh.planit.service.PlanItService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 일정 관리 API의 REST 컨트롤러입니다.
 *
 * 일정 생성, 조회, 수정, 삭제 등의 기능을 제공하며,
 * 각 요청은 PlanItService를 통해 실제 비즈니스 로직으로 위임됩니다.
 *
 * 기본 URL: /plan
 */
@RestController
@RequestMapping("/plan")
@RequiredArgsConstructor
public class PlanItController {

    private final PlanItService service;

    /**
     * 새로운 일정을 생성합니다.
     *
     * 클라이언트는 PlanItCreateRequest JSON 을 전송해야 하며,
     * 유효성 검증 통과 시 생성된 일정 정보를 반환합니다.
     *
     * @param request 일정 생성 요청 DTO (userId, title, contents)
     * @return 201 Created + 생성된 일정 정보
     */
    @PostMapping
    public ResponseEntity<PlanItResponse> createPlan(@Valid @RequestBody PlanItCreateRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.savePlan(request));
    }

    /**
     * 일정 목록을 조회합니다.
     *
     * optional 쿼리 파라미터인 date, username 을 기준으로 필터링할 수 있으며,
     * 페이지네이션이 적용됩니다.
     *
     * @param date     (옵션) 조회할 날짜 필터
     * @param username (옵션) 특정 유저의 일정만 필터링
     * @param pageable 페이지 및 정렬 정보 (기본: created_at DESC, 10개씩)
     * @return 200 OK + 페이징된 일정 목록
     */
    @GetMapping
    public ResponseEntity<Page<PlanItResponse>> getPlans(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String username,
            @PageableDefault(size = 10, sort = "created_at", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getPlans(date, username, pageable));
    }

    /**
     * 기존 일정을 수정합니다.
     *
     * 요청에는 일정 ID, 사용자 ID, 비밀번호, 변경할 내용이 포함되어야 하며,
     * 유효성 검증 후 수정된 일정 정보를 반환합니다.
     *
     * @param request 일정 수정 요청 DTO
     * @return 200 OK + 수정된 일정 정보
     */
    @PatchMapping
    public ResponseEntity<PlanItResponse> updatePlan(@Valid @RequestBody PlanItUpdateRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.updatePlan(request));
    }

    /**
     * 일정을 삭제합니다.
     *
     * 삭제는 POST 방식으로 처리되며, 사용자 ID 및 비밀번호를 통해 본인 확인을 수행합니다.
     *
     * @param request 일정 삭제 요청 DTO
     * @return 단순 문자열 메시지 (일정이 삭제되었음을 알림)
     */
    @PostMapping("/delete")
    public String deletePlan(@Valid @RequestBody PlanItDeleteRequest request) {
        service.delete(request);
        return "일정이 삭제되었습니다.";
    }
}
