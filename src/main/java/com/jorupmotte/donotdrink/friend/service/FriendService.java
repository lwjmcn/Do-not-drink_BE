package com.jorupmotte.donotdrink.friend.service;

import com.jorupmotte.donotdrink.common.dto.response.ResponseDto;
import com.jorupmotte.donotdrink.common.type.FriendStatusType;
import com.jorupmotte.donotdrink.friend.dto.request.FriendReqRequestDto;
import com.jorupmotte.donotdrink.friend.dto.request.FriendReqResRequestDto;
import com.jorupmotte.donotdrink.friend.dto.response.FriendReqRestResponseDto;
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
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendService implements IFriendService {
    private final FriendRequestRepository friendRequestRepository;
    private final FriendshipRepository friendshipRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final SseEmitterService sseEmitterService;

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

        // 알림 전송
        sseEmitterService.sendFriendRequestNotification(receiver.getId(), userMe.getNickname());

        return FriendReqResponseDto.success();
    }

    @Override
    public ResponseEntity<? super FriendReqRestResponseDto> respondToFriendRequest(Long requestId, FriendReqResRequestDto requestDto) {
        User userMe = userService.getUserFromSecurityContext();
        if(userMe == null) {
            return ResponseDto.authorizationFail();
        }

        Optional<FriendRequest> friendRequestOptional = friendRequestRepository.findById(requestId);
        if(friendRequestOptional.isEmpty()){
            return ResponseDto.databaseError();
        }
        FriendRequest friendRequest = friendRequestOptional.get();

        if(!Objects.equals(friendRequest.getTo().getId(), userMe.getId())){
            return ResponseDto.databaseError();
        }
        if(isFriend(userMe.getId(), friendRequest.getFrom().getId())){
            return ResponseDto.databaseError();
        }


        FriendStatusType status = requestDto.getStatus();

        // 친구 요청 상태 변경
        friendRequestRepository.updateStatusById(requestId, status);

        // 알림 전송
        sseEmitterService.sendFriendRequestUpdate(userMe.getId(), friendRequest.getFrom().getNickname(), status);


        if (status == FriendStatusType.ACCEPT) {
            // 친구 등록
            friendshipRepository.save(Friendship.builder()
                    .user(userMe)
                    .friend(friendRequest.getFrom())
                    .build());
            friendshipRepository.save(Friendship.builder()
                    .user(friendRequest.getFrom())
                    .friend(userMe)
                    .build());
        }

        return FriendReqRestResponseDto.success();
    }
}
