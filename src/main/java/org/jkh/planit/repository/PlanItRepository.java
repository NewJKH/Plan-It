package org.jkh.planit.repository;

import org.jkh.planit.dto.request.PlanItUpdateRequest;
import org.jkh.planit.dto.response.PlanItResponse;
import org.jkh.planit.entity.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface PlanItRepository {

    Optional<Plan> get(int schedulerId);

    PlanItResponse save(Plan plan);

    List<PlanItResponse> getPlansByDate(Timestamp timestamp);
    Page<PlanItResponse> getPlansByDate(Timestamp timestamp, Pageable pageable);

    List<PlanItResponse> getPlansByUserId(int userId);
    Page<PlanItResponse> getPlansByUserId(int userId, Pageable pageable);

    int update(PlanItUpdateRequest request);

    int deletePlan(int scheduleId);
}
