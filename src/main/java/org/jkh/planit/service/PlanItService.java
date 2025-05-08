package org.jkh.planit.service;

import org.jkh.planit.dto.request.PlanRequest;
import org.jkh.planit.dto.response.PlanResponse;

public interface PlanItService {

    PlanResponse savePlan(PlanRequest request);
}
