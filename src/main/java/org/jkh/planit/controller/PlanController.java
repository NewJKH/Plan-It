package org.jkh.planit.controller;

import org.jkh.planit.dto.request.PlanRequest;
import org.jkh.planit.dto.response.PlanResponse;
import org.jkh.planit.service.PlanItService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plan")
public class PlanController {
    private final PlanItService service;

    public PlanController(PlanItService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<PlanResponse> createPlan(@RequestBody PlanRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.savePlan(request));
    }
}
