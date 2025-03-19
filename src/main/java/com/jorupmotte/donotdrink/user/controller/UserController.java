package com.jorupmotte.donotdrink.user.controller;

//import com.jorupmotte.donotdrink.user.dto.response.UserFriendResponseDto;
import com.jorupmotte.donotdrink.user.dto.response.UserMeResponseDto;
import com.jorupmotte.donotdrink.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<? super UserMeResponseDto> getCurrentUser() {
        return userService.getCurrentUser();
    }

//    @GetMapping("/{accountId}")
//    public ResponseEntity<? super UserFriendResponseDto> getFriend(@PathVariable("accountId") String accountId) {
//        return userService.getFriend(accountId);
//    }

}
