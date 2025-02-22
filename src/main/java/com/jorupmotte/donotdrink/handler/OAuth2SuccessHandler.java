package com.jorupmotte.donotdrink.handler;

import com.jorupmotte.donotdrink.auth.model.CustomOAuth2User;
import com.jorupmotte.donotdrink.provider.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String tokenId = customOAuth2User.getName();
        String token = jwtProvider.createJwt(tokenId);

        response.sendRedirect("http://localhost:3000/auth/oauth-response/"+token+"/3600"); // TODO: front-end url
    }
}
