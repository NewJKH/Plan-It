package org.jkh.planit.service;

import org.jkh.planit.domain.Plan;
import org.jkh.planit.dto.request.PlanRequest;
import org.jkh.planit.dto.response.PlanResponse;
import org.jkh.planit.repository.PlanItRepository;
import org.springframework.stereotype.Service;

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
}
