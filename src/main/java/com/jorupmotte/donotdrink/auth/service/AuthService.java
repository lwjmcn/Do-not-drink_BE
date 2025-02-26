package com.jorupmotte.donotdrink.auth.service;

import com.jorupmotte.donotdrink.auth.dto.request.*;
import com.jorupmotte.donotdrink.auth.dto.response.*;
import com.jorupmotte.donotdrink.auth.model.SocialLogin;
import com.jorupmotte.donotdrink.auth.repository.*;
import com.jorupmotte.donotdrink.dto.response.ResponseDto;
import com.jorupmotte.donotdrink.auth.model.LocalLogin;
import com.jorupmotte.donotdrink.model.Theme;
import com.jorupmotte.donotdrink.auth.model.User;
import com.jorupmotte.donotdrink.auth.model.Verification;
import com.jorupmotte.donotdrink.provider.EmailProvider;
import com.jorupmotte.donotdrink.provider.JwtProvider;
import com.jorupmotte.donotdrink.type.LoginType;
import com.jorupmotte.donotdrink.type.SocialLoginType;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService{
    private final UserRepository userRepository;
    private final LocalLoginRepository localLoginRepository;
    private final VerificationRepository verificationRepository;
    private final ThemeRepository themeRepository;

    private final JwtProvider jwtProvider;
    private final EmailProvider emailProvider;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 직접 의존성 주입
    private final SocialLoginRepository socialLoginRepository;


    @Override
    public ResponseEntity<? super AccountIdCheckResponseDto> accountIdCheck(AccountIdCheckRequestDto requestDto) {
        try {
            String userAccountId = requestDto.getAccountId();
            boolean isExist = userRepository.existsByAccountId(userAccountId);
            if(isExist)
                return AccountIdCheckResponseDto.duplicateId();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return AccountIdCheckResponseDto.success();
    }

    @Override
    public ResponseEntity<? super EmailVerificationResponseDto> emailVerification(EmailVerificationRequestDto requestDto) {
        try {
            String userEmail = requestDto.getEmail();

            Optional<LocalLogin> localLogin = localLoginRepository.getByEmail(userEmail);
            if(localLogin.isEmpty())
                return EmailVerificationResponseDto.noEmail();

            // 인증코드 발급
            StringBuilder verificationCode = new StringBuilder();
            for (int i = 0; i < 6; i++)
                verificationCode.append((int) (Math.random() * 10));

            boolean isSuccess = emailProvider.sendVerificationEmail(userEmail, verificationCode.toString());
            if(!isSuccess)
                return EmailVerificationResponseDto.mailSendFail();

            // 인증코드 DB에 저장
            Verification verification = Verification.builder()
                    .localLogin(localLogin.get())
                    .verificationCode(verificationCode.toString())
                    .build();
            verificationRepository.save(verification);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return EmailVerificationResponseDto.success();
    }

    @Override
    public ResponseEntity<? super CheckVerificationResponseDto> checkVerification(CheckVerificationRequestDto requestDto) {
        try {
            String userEmail = requestDto.getEmail();
            String verificationCode = requestDto.getVerificationCode();

            Optional<LocalLogin> localLogin = localLoginRepository.getByEmail(userEmail);
            if(localLogin.isEmpty())
                return CheckVerificationResponseDto.noEmail();
            Optional<Verification> verification = verificationRepository.findTopByLocalLoginIdOrderByCreatedAtDesc(localLogin.get().getId());
            if(verification.isEmpty())
                return CheckVerificationResponseDto.verificationFail();

            Verification v = verification.get();

            // 유효시간 5분
            if(!v.getCreatedAt().isAfter(LocalDateTime.now().minusMinutes(5)))
                return CheckVerificationResponseDto.verificationFail();
            // 일치
            if(!v.getVerificationCode().equals(verificationCode))
                return CheckVerificationResponseDto.verificationFail();

        } catch (Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return CheckVerificationResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto requestDto) {
        try {
            String email = requestDto.getEmail();
            String password = requestDto.getPassword();
            String accountId = requestDto.getAccountId();
            Long themeId = requestDto.getThemeId();

            if(userRepository.existsByAccountId(accountId))
                return SignUpResponseDto.duplicateId();
            if(localLoginRepository.existsByEmail(email))
                return SignUpResponseDto.duplicateEmail();

            // User 생성
            // with nickname, accountId, login_type LOCAL, role USER, theme_id
            Optional<Theme> theme = themeRepository.findById(themeId);
            if(theme.isEmpty())
                return ResponseDto.databaseError();
            User user = new User(requestDto, theme.get());
            userRepository.save(user);

            // LocalLogin 생성
            // with user_id, email, encodedPassword
            String encodedPassword = passwordEncoder.encode(password);
            LocalLogin localLogin = new LocalLogin(user, email, encodedPassword);
            localLoginRepository.save(localLogin);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignUpResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto requestDto) {
        String token = null;

        try {

            String email = requestDto.getEmail();
            String password = requestDto.getPassword();

            Optional<LocalLogin> localLogin = localLoginRepository.getByEmail(email);
            if(localLogin.isEmpty())
                return SignInResponseDto.signInFail();

            String encodedPassword = localLogin.get().getPassword();
            if(!passwordEncoder.matches(password, encodedPassword))
                return SignInResponseDto.signInFail();

            // token 생성
            token = jwtProvider.createJwt(localLogin.get().getUser().getAccountId());


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignInResponseDto.success(token);
    }

    @Override
    public ResponseEntity<? super OAuthSignUpResponseDto> oAuthSignUp(HttpSession session, OAuthSignUpRequestDto requestDto) {
        String tokenId = session.getAttribute("tokenId").toString();
        if(tokenId == null)
            return OAuthSignUpResponseDto.noSessionInfo();

        String token = null;

        try {
            // create user
            String accountId = requestDto.getAccountId();
            String nickname = requestDto.getNickname();
            Long themeId = requestDto.getThemeId();

            if(userRepository.existsByAccountId(accountId))
                return OAuthSignUpResponseDto.duplicateId();

            Optional<Theme> theme = themeRepository.findById(themeId);
            if(theme.isEmpty())
                return ResponseDto.databaseError();
            User user = new User(accountId, nickname, LoginType.SOCIAL, theme.get());

            // create social login
            SocialLogin socialLogin = new SocialLogin(user, tokenId, SocialLoginType.KAKAO);
            socialLoginRepository.save(socialLogin);

            userRepository.save(user);
            socialLoginRepository.save(socialLogin);

            session.removeAttribute("tokenId");

            // jwt 발급
            token = jwtProvider.createJwt(accountId);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return OAuthSignUpResponseDto.success(token);
    }
}
