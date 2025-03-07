package com.jorupmotte.donotdrink.auth.filter;

import com.jorupmotte.donotdrink.type.RoleType;
import com.jorupmotte.donotdrink.user.model.User;
import com.jorupmotte.donotdrink.provider.JwtProvider;
import com.jorupmotte.donotdrink.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor // IoC
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final CacheManager cacheManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String token = parseBearerToken(request);
            if((token != null) && (checkTokenBlacklist(token) == null)) { // 로그아웃 된 토큰인지 체크
                String userAccountId = jwtProvider.validateJwt(token);
                if(userAccountId != null){
                    User user = userRepository.findByAccountId(userAccountId).orElse(null);
                    if(user != null) {
                        // security context에 인증 정보를 저장
                        RoleType role = user.getRole();

                        List<GrantedAuthority> authorities = new ArrayList<>();
                        authorities.add(new SimpleGrantedAuthority(role.toString()));

                        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                        AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userAccountId, null, authorities);
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        securityContext.setAuthentication(authenticationToken);
                        SecurityContextHolder.setContext(securityContext);
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }

    private String checkTokenBlacklist(String token) {
        return cacheManager.getCache("expired").get(token, String.class);
    }
}
