package com.jorupmotte.donotdrink.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jorupmotte.donotdrink.auth.model.CustomOAuth2User;
import com.jorupmotte.donotdrink.auth.repository.SocialLoginRepository;
import com.jorupmotte.donotdrink.auth.repository.ThemeRepository;
import com.jorupmotte.donotdrink.auth.repository.UserRepository;
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

        String tokenId = null;

        if (oAuthClientName.equals("kakao")){ // baseUri/api/v1/auth/oauth2/kakao에서 테스트
            tokenId = oAuth2User.getAttribute("id").toString();
        }

        return new CustomOAuth2User(tokenId);
    }

}
