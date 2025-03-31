package com.jorupmotte.donotdrink.friend.service;

import com.jorupmotte.donotdrink.friend.dto.request.FriendReqRequestDto;
import com.jorupmotte.donotdrink.friend.dto.request.FriendReqResRequestDto;
import com.jorupmotte.donotdrink.friend.dto.response.FriendReqResResponseDto;
import com.jorupmotte.donotdrink.friend.dto.response.FriendReqListResponseDto;
import com.jorupmotte.donotdrink.friend.dto.response.FriendReqResponseDto;
import com.jorupmotte.donotdrink.friend.dto.response.FriendshipListResponseDto;
import org.springframework.http.ResponseEntity;

public interface IFriendService {
    public Long getUserIdFromRequestId(Long requestId);
    public boolean isFriend(Long userId, Long friendId);

    public ResponseEntity<? super FriendReqListResponseDto> getReceivedFriendRequests();
    public ResponseEntity<? super FriendReqResponseDto> requestFriend(FriendReqRequestDto requestDto);
    public ResponseEntity<? super FriendReqResResponseDto> respondToFriendRequest(Long requestId, FriendReqResRequestDto requestDto);
    public ResponseEntity<? super FriendshipListResponseDto> getFriends();
}
