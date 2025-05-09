package org.jkh.planit.service;

import org.jkh.planit.dto.request.PlanRequest;
import org.jkh.planit.dto.response.PlanResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlanItService {

    PlanResponse savePlan(PlanRequest request);

    List<PlanResponse> getPlansByDate(String date);
    Page<PlanResponse> getPlansByDate(String date, Pageable pageable);

    List<PlanResponse> getPlansByUsername(String username);
    Page<PlanResponse> getPlansByUsername(String username, Pageable pageable);

    PlanResponse updatePlan(PlanRequest request);

    void delete(PlanRequest request);
}
