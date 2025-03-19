package com.jorupmotte.donotdrink.budget.controller;

import com.jorupmotte.donotdrink.budget.dto.request.ReactToRequestDto;
import com.jorupmotte.donotdrink.budget.dto.response.ReactToResponseDto;
import com.jorupmotte.donotdrink.budget.service.ReactionService;
import com.jorupmotte.donotdrink.friend.service.FriendService;
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
@RequestMapping("api/v1/users/{friendId}/budgets/current/reactions")
public class BudgetFriendController {
    private final UserService userService;
    private final ReactionService reactionService;
    private final SseEmitterService sseEmitterService;
    private final FriendService friendService;

    @GetMapping("/current/reactions")
    public SseEmitter subscribeReaction(
            @PathVariable Long friendId
    ) {
        User userMe = userService.getUserFromSecurityContext();
        if(userMe == null) {
            System.out.println("reaction subscribe: authorization fail");
            return null;
        }
        if(friendService.isFriend(userMe.getId(), friendId)){
            System.out.println("reaction subscribe: not a friend");
            return null;
        }
        return sseEmitterService.reactionSubscribe(friendId);
    }

    @PostMapping("/current/reactions")
    public ResponseEntity<? super ReactToResponseDto> reactTo(
            @PathVariable Long friendId, // receiverId
            @RequestBody @Valid ReactToRequestDto requestBody) {
        return reactionService.reactTo(friendId, requestBody);
    }
}
