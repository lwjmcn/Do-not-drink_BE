package com.jorupmotte.donotdrink.friend.service;

import com.jorupmotte.donotdrink.common.dto.response.ResponseDto;
import com.jorupmotte.donotdrink.common.type.FriendStatusType;
import com.jorupmotte.donotdrink.friend.dto.request.FriendReqRequestDto;
import com.jorupmotte.donotdrink.friend.dto.response.FriendReqListResponseDto;
import com.jorupmotte.donotdrink.friend.dto.response.FriendReqResponseDto;
import com.jorupmotte.donotdrink.friend.model.FriendRequest;
import com.jorupmotte.donotdrink.friend.model.Friendship;
import com.jorupmotte.donotdrink.friend.repository.FriendRequestRepository;
import com.jorupmotte.donotdrink.friend.repository.FriendshipRepository;
import com.jorupmotte.donotdrink.user.model.User;
import com.jorupmotte.donotdrink.user.repository.UserRepository;
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
    private final UserRepository userRepository;

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

        // 읽음 처리
        friendRequestList.forEach(friendRequest -> {
            if(friendRequest.getStatus() == FriendStatusType.NOREAD) {
                friendRequestRepository.updateStatusById(friendRequest.getId(), FriendStatusType.READ);
            }
        });

        return FriendReqListResponseDto.success(friendRequestList);
    }

    @Override
    public ResponseEntity<? super FriendReqResponseDto> requestFriend(FriendReqRequestDto requestDto) {
        // 본인 정보
        User userMe = userService.getUserFromSecurityContext();
        if(userMe == null) {
            return ResponseDto.authorizationFail();
        }

        // 받는사람 정보
        Optional<User> receiverOptional = userRepository.findByAccountId(requestDto.getReceiverAccountId());
        if(receiverOptional.isEmpty()){
            return FriendReqResponseDto.noUser();
        }
        User receiver = receiverOptional.get();

        if(isFriend(userMe.getId(), receiver.getId())){
            return FriendReqResponseDto.alreadyFriend();
        }
        if(friendRequestRepository.existsByFrom_IdAndTo_Id(userMe.getId(), receiver.getId())){
            return FriendReqResponseDto.alreadyRequested();
        }

        FriendRequest friendRequest = FriendRequest.builder()
                .from(userMe)
                .to(receiver)
                .status(FriendStatusType.NOREAD)
                .build();
        friendRequestRepository.save(friendRequest);

        return FriendReqResponseDto.success();
    }
}
