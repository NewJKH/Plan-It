package org.jkh.planit.repository;

import org.jkh.planit.domain.Plan;
import org.jkh.planit.dto.request.UpdatePlanRequest;
import org.jkh.planit.dto.response.PlanResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface PlanItRepository {

    Optional<Plan> get(int schedulerId);

    PlanResponse save(Plan plan);

    List<PlanResponse> getPlansByDate(Timestamp timestamp);
    Page<PlanResponse> getPlansByDate(Timestamp timestamp, Pageable pageable);

    List<PlanResponse> getPlansByUserId(int userId);
    Page<PlanResponse> getPlansByUserId(int userId,  Pageable pageable);

    int update(UpdatePlanRequest request);

    int deletePlan(int scheduleId);
}
