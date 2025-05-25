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
import org.jkh.planit.repository.PlanItRepository;
import org.jkh.planit.repository.UserRepository;
import org.jkh.planit.util.DateTimeUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlanItServiceImpl implements PlanItService{
    private final PlanItRepository planItRepository;
    private final UserRepository userRepository;

    @Override
    public PlanItResponse savePlan(PlanItCreateRequest request) {
        Plan plan = new Plan(request.getUserId(), request.getTitle(), request.getContents());
        planItRepository.save(plan);
        return PlanItResponse.toDto(planItRepository.getPlansByScheduleId(plan.getScheduleId()));
    }

    @Override
    public Page<PlanItResponse> getPlans(String date, String username, int page ,int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "create_at"));

        if (date == null && username == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "date 또는 username 중 하나는 반드시 필요합니다.");
        }

        if( date != null ){
            return planItRepository.findPlansByDate(DateTimeUtil.toTimestamp(date), pageable)
                    .map(PlanItResponse::toDto);
        }else {
            User user = userRepository.findByUsernameOrThrow(username);
            return planItRepository.findPlansByUserId(user.getUserId(), pageable)
                    .map(PlanItResponse::toDto);
        }
    }

    @Override
    public PlanItResponse findByScheduleId(int scheduleId){
        return PlanItResponse.toDto(planItRepository.getPlansByScheduleId(scheduleId));
    }

    @Transactional
    @Override
    public PlanItResponse updatePlan(PlanItUpdateRequest request) {
        if (request.getContents().isEmpty()) {
            throw new EmptyContentException();
        }

        int row = planItRepository.update(planItRepository.getPlansByScheduleId(request.getScheduleId()));
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
        Plan plan = planItRepository.getPlansByScheduleId(request.getScheduleId());
        User user = userRepository.findByIdOrThrow(request.getUserId());
        if ( !validatePw(user.getUserPwHash(), request.getUserPw())){ // 이게 비밀번호 검증 ㅠㅠ
            throw new NotMatchedPasswordException();
        }

        planItRepository.deletePlan(plan.getScheduleId());
    }

    private boolean validatePw(String userPwHash, String requestPw){
        int request = requestPw.hashCode();

        String v = String.valueOf(request);
        return userPwHash.equals(v);
    }
}
