package com.jorupmotte.donotdrink.user.dto.response;

import com.jorupmotte.donotdrink.common.dto.response.ResponseDto;
import com.jorupmotte.donotdrink.user.model.User;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class UserMeResponseDto extends ResponseDto {
    private final UserMeDto user;

    private UserMeResponseDto(User user) {
        super();
        this.user = UserMeDto.from(user);
    }

    public static ResponseEntity<UserMeResponseDto> success(User user) {
        UserMeResponseDto responseBody = new UserMeResponseDto(user);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    private record UserMeDto(String nickname, String accountId, String fileUrl, String themeColor) {
        public static UserMeDto from(User user) {
            return new UserMeDto(
                    user.getNickname(),
                    user.getAccountId(),
                    user.getTheme().getFileUrl(),
                    user.getTheme().getColor()
            );
        }
    }
}
