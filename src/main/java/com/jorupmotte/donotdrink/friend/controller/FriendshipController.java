package com.jorupmotte.donotdrink.friend.controller;

import com.jorupmotte.donotdrink.friend.dto.response.FriendshipListResponseDto;
import com.jorupmotte.donotdrink.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users/me/friends")
public class FriendshipController {
    private final FriendService friendService;

    @GetMapping(value = "/")
    public ResponseEntity<? super FriendshipListResponseDto> getFriends(){
        return friendService.getFriends();
    }
}
