package com.jorupmotte.donotdrink.handler;

import com.jorupmotte.donotdrink.auth.model.CustomOAuth2User;
import com.jorupmotte.donotdrink.auth.model.SocialLogin;
import com.jorupmotte.donotdrink.auth.repository.SocialLoginRepository;
import com.jorupmotte.donotdrink.provider.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final SocialLoginRepository socialLoginRepository;

    private final JwtProvider jwtProvider;

    @Value("${FRONTEND_URL}")
    private String frontUrl;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException, UnsupportedEncodingException {
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String tokenId = customOAuth2User.getName();

        Optional<SocialLogin> socialLogin =  socialLoginRepository.findByTokenId(tokenId);

        if(socialLogin.isPresent()){
            // sign in
            String token = jwtProvider.createJwt(tokenId);
            response.sendRedirect(frontUrl+"/auth/oauth/signin/"+token+"/3600");
        } else {
            // sign up
            HttpSession session = request.getSession();
            session.setAttribute("tokenId", tokenId); // should be removed after sign up

            String nickname = customOAuth2User.getNickname();
            String encodedNickname = URLEncoder.encode(nickname, StandardCharsets.UTF_8);
            String accountId = customOAuth2User.getAccountId();

            getRedirectStrategy().sendRedirect(request,response,frontUrl+"/auth/oauth/signup/profile"+"?nickname="+encodedNickname+"&accountId="+accountId);
        }
    }
}


