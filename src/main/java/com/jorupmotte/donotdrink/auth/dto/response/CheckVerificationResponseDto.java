package com.jorupmotte.donotdrink.auth.dto.response;

import com.jorupmotte.donotdrink.common.dto.response.ResponseDto;
import com.jorupmotte.donotdrink.common.type.ResponseCode;
import com.jorupmotte.donotdrink.common.type.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class CheckVerificationResponseDto extends ResponseDto {
    private CheckVerificationResponseDto() {
        super();
    }

    public static ResponseEntity<CheckVerificationResponseDto> success() {
        CheckVerificationResponseDto responseBody = new CheckVerificationResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> noEmail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.NO_EMAIL, ResponseMessage.NO_EMAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> verificationFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.VERIFICATION_FAIL, ResponseMessage.VERIFICATION_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }
}
