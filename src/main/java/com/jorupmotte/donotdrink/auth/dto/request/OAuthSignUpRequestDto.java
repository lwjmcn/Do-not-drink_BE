package com.jorupmotte.donotdrink.auth.dto.request;

import com.jorupmotte.donotdrink.type.SocialLoginType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OAuthSignUpRequestDto {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$") // 영문 또는 숫자 포함 가능, 4자에서 20자 사이
    private String accountId;

    @NotBlank
    private String nickname;

    @NotBlank
    private SocialLoginType socialLoginType;
}
