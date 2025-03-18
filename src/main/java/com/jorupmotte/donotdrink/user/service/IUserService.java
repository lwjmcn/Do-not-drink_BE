package com.jorupmotte.donotdrink.user.service;

import com.jorupmotte.donotdrink.user.dto.response.UserFriendResponseDto;
import com.jorupmotte.donotdrink.user.dto.response.UserMeResponseDto;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    ResponseEntity<? super UserMeResponseDto> getCurrentUser();
    ResponseEntity<? super UserFriendResponseDto> getFriend(String friendAccountId);
}
