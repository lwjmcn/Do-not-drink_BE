package com.jorupmotte.donotdrink.friend.service;

import com.jorupmotte.donotdrink.friend.dto.response.FriendReqListResponseDto;
import org.springframework.http.ResponseEntity;

public interface IFriendService {
    public Long getUserIdFromRequestId(Long requestId);
    public boolean isFriend(Long userId, Long friendId);

    public ResponseEntity<? super FriendReqListResponseDto> getReceivedFriendRequests();
}
