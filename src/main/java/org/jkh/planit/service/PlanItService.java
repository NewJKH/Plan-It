package org.jkh.planit.service;

import org.jkh.planit.dto.request.CreatePlanRequest;
import org.jkh.planit.dto.request.DeletePlanRequest;
import org.jkh.planit.dto.request.UpdatePlanRequest;
import org.jkh.planit.dto.response.PlanResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlanItService {

    PlanResponse savePlan(CreatePlanRequest request);

    Page<PlanResponse> getPlansByDate(String date, Pageable pageable);

    Page<PlanResponse> getPlansByUsername(String username, Pageable pageable);

    PlanResponse updatePlan(UpdatePlanRequest request);

    void delete(DeletePlanRequest request);
}
