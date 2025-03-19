package com.jorupmotte.donotdrink.friend.controller;

import com.jorupmotte.donotdrink.friend.service.SseEmitterService;
import com.jorupmotte.donotdrink.friend.dto.response.FriendReqListResponseDto;
import com.jorupmotte.donotdrink.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users/me/friend-requests")
public class FriendRequestController {

    private final SseEmitterService sseEmitterService;
    private final FriendService friendService;

    @GetMapping(value = "/received")
    public ResponseEntity<? super FriendReqListResponseDto> getReceivedFriendRequests() {
        return friendService.getReceivedFriendRequests();
    }

    @PatchMapping(value = "/{requestId}/accept", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter acceptFriendRequest(@PathVariable Long requestId) {
        Long userId = friendService.getUserIdFromRequestId(requestId);
        if(userId == null) {
            sseEmitterService.sendError(userId, "Invalid request id");
        }

        return sseEmitterService.reactionSubscribe(userId);
    }

}
