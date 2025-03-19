package com.jorupmotte.donotdrink.budget.controller;

import com.jorupmotte.donotdrink.budget.dto.request.BudgetSetRequestDto;
import com.jorupmotte.donotdrink.budget.dto.response.BudgetRemainingResponseDto;
import com.jorupmotte.donotdrink.budget.dto.response.BudgetSetResponseDto;
import com.jorupmotte.donotdrink.budget.dto.response.ReactionCurrentResponseDto;
import com.jorupmotte.donotdrink.budget.service.BudgetService;
import com.jorupmotte.donotdrink.budget.service.ReactionService;
import com.jorupmotte.donotdrink.friend.service.SseEmitterService;
import com.jorupmotte.donotdrink.user.model.User;
import com.jorupmotte.donotdrink.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users/me/budgets")
public class BudgetMeController {
    private final BudgetService budgetService;
    private final SseEmitterService sseEmitterService;
    private final UserService userService;

    @GetMapping("/current/remains")
    public ResponseEntity<? super BudgetRemainingResponseDto> getRemainingBudget() {
        return budgetService.getRemainingBudget();
    }

    @PostMapping("/current")
    public ResponseEntity<? super BudgetSetResponseDto> setBudget(
            @RequestBody @Valid BudgetSetRequestDto requestBody
    ) {
        return budgetService.setBudget(requestBody);
    }

    @GetMapping(value = "/current/reactions")
    public SseEmitter subscribeMyReaction() {
        User userMe = userService.getUserFromSecurityContext();
        if(userMe == null){
            System.out.println("reaction subscribe: authorization fail");
            return null;
        }

        return sseEmitterService.reactionSubscribe(userMe.getId());
    }
//    public ResponseEntity<? super ReactionCurrentResponseDto> getMyCurrentReactions() {
//        return reactionService.getMyCurrentReactionsFromDb();
//    }
}
