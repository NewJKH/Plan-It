package org.jkh.planit.controller;

import lombok.RequiredArgsConstructor;
import org.jkh.planit.dto.request.PlanRequest;
import org.jkh.planit.dto.response.PlanResponse;
import org.jkh.planit.service.PlanItService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/plan")
@RequiredArgsConstructor
public class PlanController {
    private final PlanItService service;

    @PostMapping
    public ResponseEntity<PlanResponse> createPlan(@RequestBody PlanRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.savePlan(request));
    }

    @GetMapping
    public ResponseEntity<List<PlanResponse>> getPlans(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String username) {
        if (date != null) {
            return ResponseEntity.ok(service.getPlansByDate(date));
        } else if (username != null) {
            return ResponseEntity.ok(service.getPlansByUsername(username));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping
    public ResponseEntity<PlanResponse> updatePlan(@RequestBody PlanRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.updatePlan(request));
    }

    @PostMapping("/delete")
    public String deletePlan(@RequestBody PlanRequest request){
        service.delete(request);
        return "일정이 삭제되었습니다.";
    }
}
