package org.jkh.planit.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jkh.planit.dto.request.CreatePlanRequest;
import org.jkh.planit.dto.request.DeletePlanRequest;
import org.jkh.planit.dto.request.UpdatePlanRequest;
import org.jkh.planit.dto.response.PlanResponse;
import org.jkh.planit.service.PlanItService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/plan")
@RequiredArgsConstructor
public class PlanController {
    private final PlanItService service;

    @PostMapping
    public ResponseEntity<PlanResponse> createPlan(@Valid @RequestBody CreatePlanRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.savePlan(request));
    }
//
//    @GetMapping
//    public ResponseEntity<List<PlanResponse>> getPlans(
//            @RequestParam(required = false) String date,
//            @RequestParam(required = false) String username) {
//        if (date != null) {
//            return ResponseEntity.ok(service.getPlansByDate(date));
//        } else if (username != null) {
//            return ResponseEntity.ok(service.getPlansByUsername(username));
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping
    public ResponseEntity<Page<PlanResponse>> getPlans(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String username,
            @PageableDefault(size = 10, sort = "created_at", direction = Sort.Direction.DESC) Pageable pageable) {
        if (date != null) {
            return ResponseEntity
                    .ok(service.getPlansByDate(date, pageable));
        } else if (username != null) {
            return ResponseEntity
                    .ok(service.getPlansByUsername(username, pageable));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
}

    @PatchMapping
    public ResponseEntity<PlanResponse> updatePlan(@RequestBody UpdatePlanRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.updatePlan(request));
    }

    @PostMapping("/delete")
    public String deletePlan(@Valid @RequestBody DeletePlanRequest request){
        service.delete(request);
        return "일정이 삭제되었습니다.";
    }
}
