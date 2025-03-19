package com.jorupmotte.donotdrink.budget.controller;

import com.jorupmotte.donotdrink.budget.dto.request.ReactToRequestDto;
import com.jorupmotte.donotdrink.budget.dto.response.ReactToResponseDto;
import com.jorupmotte.donotdrink.budget.service.ReactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users/{receiverId}/budgets/current/reactions")
public class BudgetFriendController {
    private final ReactionService reactionService;

    @PostMapping("/current/reactions")
    public ResponseEntity<? super ReactToResponseDto> reactTo(
            @PathVariable Long receiverId,
            @RequestBody @Valid ReactToRequestDto requestBody) {
        return reactionService.reactTo(receiverId, requestBody);
    }
}
