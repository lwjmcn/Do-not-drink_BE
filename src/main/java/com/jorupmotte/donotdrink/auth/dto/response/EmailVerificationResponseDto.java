package com.jorupmotte.donotdrink.auth.dto.response;

import com.jorupmotte.donotdrink.common.dto.response.ResponseDto;
import com.jorupmotte.donotdrink.common.type.ResponseCode;
import com.jorupmotte.donotdrink.common.type.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class EmailVerificationResponseDto extends ResponseDto {
    private EmailVerificationResponseDto() {
        super();
    }

    public static ResponseEntity<EmailVerificationResponseDto> success() {
        EmailVerificationResponseDto responseBody = new EmailVerificationResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> noEmail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.NO_EMAIL, ResponseMessage.NO_EMAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> mailSendFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.MAIL_SEND_FAIL, ResponseMessage.MAIL_SEND_FAIL);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }
}
