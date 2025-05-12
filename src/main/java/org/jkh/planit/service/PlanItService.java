package org.jkh.planit.service;

import org.jkh.planit.dto.request.PlanItCreateRequest;
import org.jkh.planit.dto.request.PlanItDeleteRequest;
import org.jkh.planit.dto.request.PlanItUpdateRequest;
import org.jkh.planit.dto.response.PlanItResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlanItService {

    PlanItResponse savePlan(PlanItCreateRequest request);

    Page<PlanItResponse> getPlans(String date, String username,Pageable pageable);

    PlanItResponse updatePlan(PlanItUpdateRequest request);

    void delete(PlanItDeleteRequest request);
}
