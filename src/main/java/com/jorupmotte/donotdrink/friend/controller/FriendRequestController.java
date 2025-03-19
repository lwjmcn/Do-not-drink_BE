package com.jorupmotte.donotdrink.friend.controller;

import com.jorupmotte.donotdrink.friend.dto.request.FriendReqRequestDto;
import com.jorupmotte.donotdrink.friend.dto.request.FriendReqResRequestDto;
import com.jorupmotte.donotdrink.friend.dto.response.FriendReqRestResponseDto;
import com.jorupmotte.donotdrink.friend.service.SseEmitterService;
import com.jorupmotte.donotdrink.friend.dto.response.FriendReqListResponseDto;
import com.jorupmotte.donotdrink.friend.service.FriendService;
import com.jorupmotte.donotdrink.user.model.User;
import com.jorupmotte.donotdrink.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users/me/friend-requests")
public class FriendRequestController {

    private final SseEmitterService sseEmitterService;
    private final FriendService friendService;
    private final UserService userService;

    @GetMapping(value = "/received")
    public ResponseEntity<? super FriendReqListResponseDto> getReceivedFriendRequests() {
        return friendService.getReceivedFriendRequests();
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> requestFriend(
            @RequestBody @Valid FriendReqRequestDto requestBody
    ) {
        return friendService.requestFriend(requestBody);
    }

    @GetMapping("/")
    public SseEmitter friendRequestSubscribe() {
        User user = userService.getUserFromSecurityContext();
        if (user == null) {
            return null;
        }
        return sseEmitterService.friendRequestSubscribe(user.getId());
    }

    @PatchMapping(value = "/{requestId}")
    public ResponseEntity<? super FriendReqRestResponseDto> respondToFriendRequest(
            @PathVariable Long requestId,
            @RequestBody @Valid FriendReqResRequestDto requestBody
    ) {
        return friendService.respondToFriendRequest(requestId, requestBody);
    }

}
