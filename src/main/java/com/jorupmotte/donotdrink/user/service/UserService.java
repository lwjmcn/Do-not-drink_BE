package com.jorupmotte.donotdrink.user.service;

import com.jorupmotte.donotdrink.common.dto.response.ResponseDto;
import com.jorupmotte.donotdrink.user.dto.response.UserMeResponseDto;
import com.jorupmotte.donotdrink.user.model.User;
import com.jorupmotte.donotdrink.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<? super UserMeResponseDto> getCurrentUser() {
        // Security Context에서 인증 정보 꺼내기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return ResponseDto.authorizationFail();
        }

        String userAccountId = authentication.getPrincipal().toString();

        // 유저 정보 가져오기
        Optional<User> userOptional = userRepository.findByAccountId(userAccountId);
        if(userOptional.isEmpty()){
            return ResponseDto.databaseError();
        }

        User user = userOptional.get();

        return UserMeResponseDto.success(user);
    }
}
