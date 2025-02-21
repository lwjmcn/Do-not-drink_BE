package com.jorupmotte.donotdrink.auth.dto.response;

import com.jorupmotte.donotdrink.dto.response.ResponseDto;
import com.jorupmotte.donotdrink.type.ResponseCode;
import com.jorupmotte.donotdrink.type.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class AccountIdCheckResponseDto extends ResponseDto {
    private AccountIdCheckResponseDto() {
        super();
    }

    public static ResponseEntity<AccountIdCheckResponseDto> success() {
        AccountIdCheckResponseDto responseBody = new AccountIdCheckResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> duplicateId(){
        ResponseDto responseBody = new ResponseDto(ResponseCode.DUPLICATE_ID, ResponseMessage.DUPLICATE_ID);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}
