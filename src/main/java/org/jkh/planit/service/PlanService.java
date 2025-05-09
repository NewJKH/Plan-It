package org.jkh.planit.service;

import lombok.RequiredArgsConstructor;
import org.jkh.planit.domain.Plan;
import org.jkh.planit.dto.request.PlanRequest;
import org.jkh.planit.dto.response.PlanResponse;
import org.jkh.planit.exception.EmptyContentException;
import org.jkh.planit.exception.PlanNotFoundException;
import org.jkh.planit.repository.PlanItRepository;
import org.jkh.planit.repository.PlanRepositoryImpl;
import org.jkh.planit.util.DateTimeUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class PlanService implements PlanItService{
    private final PlanItRepository planItRepository;
    private final PlanRepositoryImpl planRepositoryImpl;

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

    @Override
    public PlanResponse updatePlan(PlanRequest request) {
        if (request.getContents().isEmpty()) {
            throw new EmptyContentException();
        }

        int row = planItRepository.update(request);
        if (row == 0) {
            throw new PlanNotFoundException();
        }

        return planRepositoryImpl.get(request.getScheduleId())
                .map(plan -> new PlanResponse(
                        plan.getScheduleId(),
                        plan.getUserId(),
                        plan.getTitle(),
                        plan.getContents()))
                .orElseThrow(() -> new PlanNotFoundException("존재하지 않는 일정입니다."));
    }


    @Override
    public void delete(PlanRequest request) {
        // 비밀번호 일치하는지 로직 추가

        int row = planItRepository.deletePlan(request.getScheduleId());
        if ( row == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
