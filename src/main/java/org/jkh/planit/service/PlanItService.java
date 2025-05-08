package org.jkh.planit.service;

import org.jkh.planit.dto.request.PlanRequest;
import org.jkh.planit.dto.response.PlanResponse;

import java.util.List;

public interface PlanItService {

    PlanResponse savePlan(PlanRequest request);

    List<PlanResponse> getPlansByDate(String date);

    List<PlanResponse> getPlansByUsername(String username);

    PlanResponse updatePlan(PlanRequest request);
}
