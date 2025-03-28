package com.jorupmotte.donotdrink.friend.dto.response;

import com.jorupmotte.donotdrink.common.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class FriendReqRestResponseDto extends ResponseDto {
    public FriendReqRestResponseDto() {
        super();
    }

    public static ResponseEntity<FriendReqRestResponseDto> success() {
        FriendReqRestResponseDto responseBody = new FriendReqRestResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
