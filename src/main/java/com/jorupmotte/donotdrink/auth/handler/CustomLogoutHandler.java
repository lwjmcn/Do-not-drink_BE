package com.jorupmotte.donotdrink.auth.handler;

import com.jorupmotte.donotdrink.common.config.CacheConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {
    private final CacheManager cacheManager;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = parseBearerToken(request);
        if(token == null) return;

        registerInExpiredToken(token); // blacklist cache에 등록
    }

    private String parseBearerToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        System.out.println("authorization: " + authorization);
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }

    private void registerInExpiredToken(String token) {
        cacheManager.getCache(CacheConfig.JWT_BLACKLIST).put(token, "expired");
        System.out.println(token + " is expired");
    }
}
