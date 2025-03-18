package com.jorupmotte.donotdrink.budget.service;

import com.jorupmotte.donotdrink.budget.dto.request.ReactToRequestDto;
import com.jorupmotte.donotdrink.budget.dto.response.ReactToResponseDto;
import com.jorupmotte.donotdrink.budget.dto.response.ReactionCurrentResponseDto;
import com.jorupmotte.donotdrink.budget.model.Budget;
import com.jorupmotte.donotdrink.budget.model.Reaction;
import com.jorupmotte.donotdrink.budget.repository.ReactionRepository;
import com.jorupmotte.donotdrink.common.dto.response.ResponseDto;
import com.jorupmotte.donotdrink.user.model.User;
import com.jorupmotte.donotdrink.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jorupmotte.donotdrink.user.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReactionService implements IReactionService {
    private final UserService userService;
    private final BudgetService budgetService;
    private final UserRepository userRepository;
    private final ReactionRepository reactionRepository;

    @Override
    public ResponseEntity<? super ReactionCurrentResponseDto> getMyCurrentReactions() {
        User userMe = userService.getUserFromSecurityContext();
        if(userMe == null) {
            return ResponseDto.authorizationFail();
        }

        // 현재 budget 조회
        Optional<Budget> budgetOptional = budgetService.getCurrentBudget(userMe.getId());
        if(budgetOptional.isEmpty()){
            return ReactionCurrentResponseDto.noBudget();
        }

        // Reaction 조회
        List<Reaction> reactionList =  reactionRepository.findAllByBudget_Id(budgetOptional.get().getId());

        return ReactionCurrentResponseDto.success(reactionList);
    }

    @Override
    public ResponseEntity<? super ReactToResponseDto> reactTo(Long friendId, ReactToRequestDto requestDto) {
        User userMe = userService.getUserFromSecurityContext();
        if(userMe == null) {
            return ResponseDto.authorizationFail();
        }

        // 친구 budget 조회
        Optional<Budget> budgetOptional = budgetService.getCurrentBudget(friendId);
        if(budgetOptional.isEmpty()){
            return ResponseDto.databaseError();
        }

        // Reaction 조회
        List<Reaction> reactionList =  reactionRepository.findAllByBudget_Id(budgetOptional.get().getId());
        
        // TODO 배치에 저장하고, 다른 함수를 써서 배치에서 처리하도록 변경하자
//        if(reactionList.isEmpty()){
//            Reaction reaction = Reaction.builder()
//                    .budget(budgetOptional.get())
//                    .user(userMe)
//                    .reactionType(requestDto.getReactionType())
//                    .count(1)
//                    .build();
//            reactionRepository.save(reaction);
//            // TODO 생성되고 있는데 Request가 다시 들어오면? lock을 걸어야 하나?
//        } else {
//            // TODO 업데이트
//        }

        return ReactToResponseDto.success();
    }
}
