package org.jkh.planit.service;

import lombok.RequiredArgsConstructor;
import org.jkh.planit.domain.Plan;
import org.jkh.planit.domain.User;
import org.jkh.planit.dto.request.PlanRequest;
import org.jkh.planit.dto.response.PlanResponse;
import org.jkh.planit.exception.EmptyContentException;
import org.jkh.planit.exception.NotMatchedPasswordException;
import org.jkh.planit.exception.PlanNotFoundException;
import org.jkh.planit.exception.UserNotFoundException;
import org.jkh.planit.repository.PlanItRepository;
import org.jkh.planit.repository.PlanRepositoryImpl;
import org.jkh.planit.repository.UserRepository;
import org.jkh.planit.util.DateTimeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class PlanService implements PlanItService{
    private final PlanItRepository planItRepository;
    private final PlanRepositoryImpl planRepositoryImpl;
    private final UserRepository userRepository;

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
        return userRepository.findByUsername(username)
                .map(user-> planItRepository.getPlansByUserId(user.getUserId()))
                .orElseThrow(UserNotFoundException::new);
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
        Optional<User> userOpt = userRepository.findById(request.getUserId());
        if ( userOpt.isEmpty()){
            throw new UserNotFoundException();
        }
        User user = userOpt.get();
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
