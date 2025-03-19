package com.jorupmotte.donotdrink.friend.dto.response;

import com.jorupmotte.donotdrink.common.dto.response.ResponseDto;
import com.jorupmotte.donotdrink.common.type.FriendStatusType;
import com.jorupmotte.donotdrink.friend.model.FriendRequest;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class FriendReqListResponseDto extends ResponseDto {
    private final FriendReqDto[] friendRequests;

    public FriendReqListResponseDto(List<FriendRequest> friendRequests) {
        super();
        this.friendRequests = friendRequests.stream()
                .map(FriendReqDto::from)
                .toArray(FriendReqDto[]::new);
    }

    public static ResponseEntity<FriendReqListResponseDto> success(List<FriendRequest> friendRequests) {
        FriendReqListResponseDto responseBody = new FriendReqListResponseDto(friendRequests);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    private record FriendReqDto(Long requestId, Long userId, String nickname, String accountId, FriendStatusType status) {
        public static FriendReqDto from(FriendRequest friendRequest) {
            return new FriendReqDto(
                    friendRequest.getId(),
                    friendRequest.getFrom().getId(), // 요청자의 정보 반환
                    friendRequest.getFrom().getNickname(),
                    friendRequest.getFrom().getAccountId(),
                    friendRequest.getStatus()
            );
        }
    }
}
