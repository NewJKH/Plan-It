package org.jkh.planit.repository;

import org.jkh.planit.entity.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.Optional;

public interface PlanItRepository {

    Optional<Plan> get(int schedulerId);

    void save(Plan plan);

    Page<Plan> findPlansByDate(Timestamp timestamp, Pageable pageable);

    Page<Plan> findPlansByUserId(int userId, Pageable pageable);

    int update(Plan request);

    int deletePlan(int scheduleId);

    Plan getPlansByScheduleId(int scheduleId);
}
