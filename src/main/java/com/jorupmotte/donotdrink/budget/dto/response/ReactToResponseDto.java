package com.jorupmotte.donotdrink.budget.dto.response;

import com.jorupmotte.donotdrink.common.dto.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jorupmotte.donotdrink.common.type.ResponseCode;
import com.jorupmotte.donotdrink.common.type.ResponseMessage;

public class ReactToResponseDto extends ResponseDto {
    private ReactToResponseDto(){
        super();
    }

    public static ResponseEntity<ReactToResponseDto> success(){
        ReactToResponseDto responseBody = new ReactToResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> notFriend(){
        ResponseDto responseBody = new ResponseDto(ResponseCode.NOT_FRIEND, ResponseMessage.NOT_FRIEND);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> tooManyRequests(){
        ResponseDto responseBody = new ResponseDto(ResponseCode.TOO_MANY_REQUESTS, ResponseMessage.TOO_MANY_REQUESTS);
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(responseBody);
    }
}
