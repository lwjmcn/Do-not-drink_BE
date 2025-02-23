package com.jorupmotte.donotdrink.auth.dto.response;

import com.jorupmotte.donotdrink.dto.response.ResponseDto;
import com.jorupmotte.donotdrink.type.ResponseCode;
import com.jorupmotte.donotdrink.type.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class OAuthSignUpResponseDto extends ResponseDto {

    private String token;
    private int expirationTime;

    private OAuthSignUpResponseDto(String token) {
        super();
        this.token = token;
        this.expirationTime = 3600; // 1 hour

    }

    public static ResponseEntity<OAuthSignUpResponseDto> success(String token) {
        OAuthSignUpResponseDto responseBody = new OAuthSignUpResponseDto(token);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> duplicateId() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.DUPLICATE_ID, ResponseMessage.DUPLICATE_ID);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> noSessionInfo() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.NO_SESSION_INFO, ResponseMessage.NO_SESSION_INFO);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}
