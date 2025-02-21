package com.jorupmotte.donotdrink.auth.controller;

import com.jorupmotte.donotdrink.auth.dto.request.*;
import com.jorupmotte.donotdrink.auth.dto.response.*;
import com.jorupmotte.donotdrink.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//request body 반환 // @Controller : html 반환
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/account-id-check")
    public ResponseEntity<? super AccountIdCheckResponseDto> idCheck(
            @RequestBody @Valid AccountIdCheckRequestDto requestBody
    ){
        return authService.accountIdCheck(requestBody);
    }

    @PostMapping("/email-verification")
    public ResponseEntity<? super EmailVerificationResponseDto> emailVerification(
            @RequestBody @Valid EmailVerificationRequestDto requestBody
    ){
        return authService.emailVerification(requestBody);
    }

    @PostMapping("/check-verification")
    public ResponseEntity<? super CheckVerificationResponseDto> checkVerification(
            @RequestBody @Valid CheckVerificationRequestDto requestBody
    ){
        return authService.checkVerification(requestBody);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<? super SignUpResponseDto> signUp(
            @RequestBody @Valid SignUpRequestDto requestBody
    ){
        return authService.signUp(requestBody);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDto> signIn(
            @RequestBody @Valid SignInRequestDto requestBody
    ){
        return authService.signIn(requestBody);
    }
}
