package com.jorupmotte.donotdrink.friend.dto.response;

import com.jorupmotte.donotdrink.common.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class FriendReqResResponseDto extends ResponseDto {
    public FriendReqResResponseDto() {
        super();
    }

    public static ResponseEntity<FriendReqResResponseDto> success() {
        FriendReqResResponseDto responseBody = new FriendReqResResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
