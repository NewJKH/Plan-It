package org.jkh.planit.repository.plan;

import org.jkh.planit.domain.Plan;
import org.jkh.planit.dto.request.PlanRequest;
import org.jkh.planit.dto.response.PlanResponse;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface PlanItRepository {

    Optional<Plan> get(int schedulerId);

    PlanResponse save(Plan plan);

    List<PlanResponse> getPlansByDate(Timestamp timestamp);

    List<PlanResponse> getPlansByUserId(int userId);

    int update(PlanRequest request);

    int deletePlan(int scheduleId);
}
