package com.jorupmotte.donotdrink.friend.controller;

import com.jorupmotte.donotdrink.budget.service.SseEmitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users/me/friend-requests")
public class FriendRequestController {

    private final SseEmitterService sseEmitterService;

    @PatchMapping(value = "/{requestId}/accept", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter acceptFriendRequest(@PathVariable Long requestId) {
//        return sseEmitterService.subscribe(userId);
        // TODO requestId에서 userId 추출해야
        return new SseEmitter();
    }
}
