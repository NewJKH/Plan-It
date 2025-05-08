package org.jkh.planit.repository;

import org.jkh.planit.domain.Plan;
import org.jkh.planit.dto.response.PlanResponse;

public interface PlanItRepository {

    PlanResponse save(Plan plan);
}
