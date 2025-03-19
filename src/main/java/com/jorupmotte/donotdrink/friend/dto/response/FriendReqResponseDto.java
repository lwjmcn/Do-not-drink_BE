package com.jorupmotte.donotdrink.friend.dto.response;

import com.jorupmotte.donotdrink.common.dto.response.ResponseDto;
import com.jorupmotte.donotdrink.common.type.ResponseCode;
import com.jorupmotte.donotdrink.common.type.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class FriendReqResponseDto extends ResponseDto {
    public FriendReqResponseDto() {
        super();
    }

    public static ResponseEntity<FriendReqResponseDto> success() {
        FriendReqResponseDto responseBody = new FriendReqResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> noUser() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> alreadyFriend() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.ALREADY_FRIEND, ResponseMessage.ALREADY_FRIEND);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> alreadyRequested() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.ALREADY_REQUESTED, ResponseMessage.ALREADY_REQUESTED);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> selfRequest() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.SELF_REQUEST, ResponseMessage.SELF_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}
