package org.jkh.planit.service;

import lombok.RequiredArgsConstructor;
import org.jkh.planit.dto.request.PlanItCreateRequest;
import org.jkh.planit.dto.request.PlanItDeleteRequest;
import org.jkh.planit.dto.request.PlanItUpdateRequest;
import org.jkh.planit.dto.response.PlanItResponse;
import org.jkh.planit.entity.Plan;
import org.jkh.planit.entity.User;
import org.jkh.planit.exception.EmptyContentException;
import org.jkh.planit.exception.NotMatchedPasswordException;
import org.jkh.planit.exception.PlanNotFoundException;
import org.jkh.planit.exception.UserNotMatchedException;
import org.jkh.planit.repository.PlanItRepository;
import org.jkh.planit.util.DateTimeUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class PlanItServiceImpl implements PlanItService{
    private final PlanItRepository planItRepository;
    private final UserService userService;

    @Override
    public PlanItResponse savePlan(PlanItCreateRequest request) {
        Plan plan = new Plan(request.getUserId(), request.getTitle(), request.getContents());
        return planItRepository.save(plan);
    }

    @Override
    public Page<PlanItResponse> getPlans(String date, String username, Pageable pageable) {
        if (date != null) {
            return planItRepository.getPlansByDate(
                    DateTimeUtil.toTimestamp(date),
                    pageable
            );
        }
        else if (username != null) {
            return planItRepository.getPlansByUserId(
                    userService.getByUsername(username).getUserId(),
                    pageable);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @Override
    public PlanItResponse updatePlan(PlanItUpdateRequest request) {
        if (request.getContents().isEmpty()) {
            throw new EmptyContentException();
        }

        int row = planItRepository.update(request);
        if (row == 0) {
            throw new PlanNotFoundException();
        }

        return planItRepository.get(request.getScheduleId())
                .map(plan -> new PlanItResponse(
                        plan.getScheduleId(),
                        plan.getUserId(),
                        plan.getTitle(),
                        plan.getContents()))
                .orElseThrow(() -> new PlanNotFoundException("존재하지 않는 일정입니다."));
    }


    @Override
    public void delete(PlanItDeleteRequest request) {
        Optional<Plan> planOpt = planItRepository.get(request.getScheduleId());
        if ( planOpt.isEmpty() ){
            throw new PlanNotFoundException();
        }
        Plan plan = planOpt.get();
        if ( plan.getUserId() != request.getUserId() ){
            throw new UserNotMatchedException();
        }

        User user = userService.getByUserId(request.getUserId());
        if ( !validatePw(user.getUserPwHash(), request.getUserPw())){
            throw new NotMatchedPasswordException();
        }

        int row = planItRepository.deletePlan(request.getScheduleId());
        if ( row == 0){
            throw new PlanNotFoundException();
        }
    }

    private boolean validatePw(String userPwHash, String requestPw){
        int request = requestPw.hashCode();

        String v = String.valueOf(request);
        return userPwHash.equals(v);
    }
}
