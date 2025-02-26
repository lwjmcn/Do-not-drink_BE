package com.jorupmotte.donotdrink.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jorupmotte.donotdrink.dto.response.ResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("logout success");

        response.setStatus(HttpStatus.OK.value());
        ResponseDto responseBody =  new ResponseDto();
        response.getWriter().println(new ObjectMapper().writeValueAsString(responseBody));
        response.getWriter().flush();

    }
}
