package com.jorupmotte.donotdrink.auth.service;

import com.jorupmotte.donotdrink.auth.dto.request.*;
import com.jorupmotte.donotdrink.auth.dto.response.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
 ResponseEntity<? super AccountIdCheckResponseDto> accountIdCheck(AccountIdCheckRequestDto requestDto);
 ResponseEntity<? super EmailVerificationResponseDto> emailVerification(EmailVerificationRequestDto requestDto);
 ResponseEntity<? super CheckVerificationResponseDto> checkVerification(CheckVerificationRequestDto requestDto);
 ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto requestDto);
 ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto requestDto);
 ResponseEntity<? super OAuthSignUpResponseDto> oAuthSignUp(HttpSession session, OAuthSignUpRequestDto requestDto);
}
