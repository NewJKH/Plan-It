package org.jkh.planit.repository;

import org.jkh.planit.domain.Plan;
import org.jkh.planit.dto.response.PlanResponse;

import java.sql.Timestamp;
import java.util.List;

public interface PlanItRepository {

    PlanResponse save(Plan plan);

    List<PlanResponse> getPlansByDate(Timestamp timestamp);

    List<PlanResponse> getPlansByUserId(int userId);
}
