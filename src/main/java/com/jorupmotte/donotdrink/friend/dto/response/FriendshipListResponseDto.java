package com.jorupmotte.donotdrink.friend.dto.response;

import com.jorupmotte.donotdrink.common.dto.response.ResponseDto;
import com.jorupmotte.donotdrink.friend.model.Friendship;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class FriendshipListResponseDto extends ResponseDto {
    private final List<FriendDto> friends;

    public FriendshipListResponseDto(List<FriendDto> friends) {
        super();
        this.friends = friends;
    }

    public static ResponseEntity<FriendshipListResponseDto> success(List<FriendDto> friends) {
        FriendshipListResponseDto responseBody = new FriendshipListResponseDto(friends);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public record FriendDto(Long userId, String nickname, String accountId, String fileUrl, float remainingRate) {
        public static FriendDto from(Friendship friendship, float remainingRate) {
            return new FriendDto(
                    friendship.getFriend().getId(),
                    friendship.getFriend().getNickname(),
                    friendship.getFriend().getAccountId(),
                    friendship.getFriend().getTheme().getFileUrl(),
                    remainingRate
            );
        }
    }
}
