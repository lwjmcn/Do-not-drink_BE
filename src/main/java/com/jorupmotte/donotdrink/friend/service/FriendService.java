package com.jorupmotte.donotdrink.friend.service;

import com.jorupmotte.donotdrink.common.dto.response.ResponseDto;
import com.jorupmotte.donotdrink.friend.dto.response.FriendReqListResponseDto;
import com.jorupmotte.donotdrink.friend.model.FriendRequest;
import com.jorupmotte.donotdrink.friend.model.Friendship;
import com.jorupmotte.donotdrink.friend.repository.FriendRequestRepository;
import com.jorupmotte.donotdrink.friend.repository.FriendshipRepository;
import com.jorupmotte.donotdrink.user.dto.response.UserMeResponseDto;
import com.jorupmotte.donotdrink.user.model.User;
import com.jorupmotte.donotdrink.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendService implements IFriendService {
    private final FriendRequestRepository friendRequestRepository;
    private final FriendshipRepository friendshipRepository;
    private final UserService userService;

    @Override
    public Long getUserIdFromRequestId(Long requestId) {
        Optional<FriendRequest> friendRequestOptional = friendRequestRepository.findById(requestId);
        if(friendRequestOptional.isEmpty()){
            return null;
        }
        return friendRequestOptional.get().getTo().getId();
    }

    @Override
    public boolean isFriend(Long userId, Long friendId) {
        Optional<Friendship> friendshipOptional = friendshipRepository.findByUserIdAndFriendId(userId, friendId);
        return friendshipOptional.isPresent();
    }

    @Override
    public ResponseEntity<? super FriendReqListResponseDto> getReceivedFriendRequests() {
        User userMe = userService.getUserFromSecurityContext();
        if(userMe == null) {
            return ResponseDto.authorizationFail();
        }

        List<FriendRequest> friendRequestList = friendRequestRepository.findAllByTo_Id(userMe.getId());

        return FriendReqListResponseDto.success(friendRequestList);
    }
}
