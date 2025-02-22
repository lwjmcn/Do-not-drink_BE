package com.jorupmotte.donotdrink.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jorupmotte.donotdrink.auth.model.CustomOAuth2User;
import com.jorupmotte.donotdrink.auth.model.SocialLogin;
import com.jorupmotte.donotdrink.auth.model.User;
import com.jorupmotte.donotdrink.auth.repository.SocialLoginRepository;
import com.jorupmotte.donotdrink.auth.repository.ThemeRepository;
import com.jorupmotte.donotdrink.auth.repository.UserRepository;
import com.jorupmotte.donotdrink.model.Theme;
import com.jorupmotte.donotdrink.type.LoginType;
import com.jorupmotte.donotdrink.type.SocialLoginType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService{

    private final ThemeRepository themeRepository;
    private final UserRepository userRepository;
    private final SocialLoginRepository socialLoginRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User =  super.loadUser(request);
        String oAuthClientName = request.getClientRegistration().getClientName();

        try {
            // 터미널에 정보 출력
            System.out.println(new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        User user = null;
        SocialLogin socialLogin = null;
        String tokenId = null;

        if (oAuthClientName.equals("kakao")){
            // 카카오 소셜 로그인
            // baseUri/api/v1/auth/oauth2/kakao에서 테스트

            // create user
            tokenId = oAuth2User.getAttribute("id").toString();

            // TODO
            // check if tokenId already exists in socialLogin db
            // if exist, just login
            // if not, create new user and social login

            String accountId = "kakao...";
            String nickname = oAuth2User.getAttribute("nickname");
            Optional<Theme> defaultTheme = themeRepository.findById(1L);
            if(defaultTheme.isEmpty()){
                return null;
            }
            user = new User(accountId, nickname, LoginType.SOCIAL,defaultTheme.get());

            // create social login
            socialLogin = new SocialLogin(user, tokenId, SocialLoginType.KAKAO);

        }

        userRepository.save(user);
        socialLoginRepository.save(socialLogin);

        return new CustomOAuth2User(tokenId);
    }

}
