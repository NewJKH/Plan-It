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

@RestController
@RequestMapping("/plan")
@RequiredArgsConstructor
public class PlanItController {
    private final PlanItService service;

    @PostMapping
    public ResponseEntity<PlanItResponse> createPlan(@Valid @RequestBody PlanItCreateRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.savePlan(request));
    }

    @GetMapping
    public ResponseEntity<Page<PlanItResponse>> getPlans(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String username,
            @PageableDefault(size = 10, sort = "created_at", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getPlans(date,username,pageable));
    }

    @PatchMapping
    public ResponseEntity<PlanItResponse> updatePlan(@Valid @RequestBody PlanItUpdateRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.updatePlan(request));
    }

    @PostMapping("/delete")
    public String deletePlan(@Valid @RequestBody PlanItDeleteRequest request){
        service.delete(request);
        return "일정이 삭제되었습니다.";
    }
}
