package com.jorupmotte.donotdrink.auth.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequestDto {
    @Email
    @NotBlank
    private String email;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$") // 8자 이상, 영문, 숫자 조합
    @NotBlank
    private String password;

    @NotBlank
    private String nickname;

    @NotBlank
    private String accountId;
}
