package org.jkh.planit.service;

import org.jkh.planit.domain.Plan;
import org.jkh.planit.dto.request.PlanRequest;
import org.jkh.planit.dto.response.PlanResponse;
import org.jkh.planit.repository.PlanItRepository;
import org.jkh.planit.util.DateTimeUtil;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class PlanService implements PlanItService{

    private final PlanItRepository planItRepository;

    public PlanService(PlanItRepository planItRepository) {
        this.planItRepository = planItRepository;
    }

    @Override
    public PlanResponse savePlan(PlanRequest request) {
        Plan plan = new Plan(request.getUserId(), request.getTitle(), request.getContents());

        return planItRepository.save(plan);
    }

    @Override
    public List<PlanResponse> getPlansByDate(String date) {
        Timestamp timestamp = DateTimeUtil.toTimestamp(date);
        return planItRepository.getPlansByDate(timestamp);
    }

    @Override
    public List<PlanResponse> getPlansByUsername(String username) {
        // 나중에 회원 찾기 로직 추가
        return planItRepository.getPlansByUserId(Integer.parseInt(username));
    }
}
