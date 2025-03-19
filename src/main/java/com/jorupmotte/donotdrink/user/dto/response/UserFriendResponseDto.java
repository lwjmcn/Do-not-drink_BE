package com.jorupmotte.donotdrink.user.dto.response;

import com.jorupmotte.donotdrink.common.dto.response.ResponseDto;
import com.jorupmotte.donotdrink.common.type.ResponseCode;
import com.jorupmotte.donotdrink.common.type.ResponseMessage;
import com.jorupmotte.donotdrink.user.model.User;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class UserFriendResponseDto extends ResponseDto {
    private final UserFriendDto user;

    private UserFriendResponseDto(User user) {
        super();
        this.user = UserFriendDto.from(user);
    }

    public static ResponseEntity<UserFriendResponseDto> success(User user) {
        UserFriendResponseDto responseBody = new UserFriendResponseDto(user);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    public static ResponseEntity<ResponseDto> notFriend() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.NOT_FRIEND, ResponseMessage.NOT_FRIEND);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }

    private record UserFriendDto(String nickname, String accountId, String fileUrl, float remainingRate) {
        public static UserFriendDto from(User user) {
            return new UserFriendDto(
                    user.getNickname(),
                    user.getAccountId(),
                    user.getTheme().getFileUrl(),
                    0 // TODO: remainingRate // 이 API 사용 안 할 것 같음
            );
        }
    }
}
