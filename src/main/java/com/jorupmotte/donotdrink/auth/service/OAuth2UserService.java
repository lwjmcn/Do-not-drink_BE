package com.jorupmotte.donotdrink.auth.service;

import com.jorupmotte.donotdrink.auth.model.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService{
    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User =  super.loadUser(request);
        String oAuthClientName = request.getClientRegistration().getClientName();

        String tokenId = "";
        String nickname = "";
        String accountId = "";
        try {
//            System.out.println(new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));

            if (oAuthClientName.equals("kakao")){ // baseUri/api/v1/auth/oauth2/kakao에서 테스트

                tokenId = oAuth2User.getAttribute("id").toString();

                Map<String, Object> properties = oAuth2User.getAttribute("properties");
                nickname = properties.get("nickname").toString();

            }
            // naver
            // google

        } catch (Exception e) {
            e.printStackTrace();
        }



        return new CustomOAuth2User(tokenId, nickname, accountId);
    }

}
