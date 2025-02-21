//package com.jorupmotte.donotdrink.user;
//
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class UserService {
//    private final UserRepository userRepository;
//
//    public String signUp(){
//        Optional<User> findUser = userRepository.findByAccountId("accountId");
//        if(findUser.isPresent())
//            return "ID already exists";
//        User user = User.builder()
//                .accountId("accountId")
//                .nickname("nickname")
//                .loginType(LoginType.LOCAL)
//                .theme(ThemeModel.builder().id(1L).build())
//                .isDeleted(false)
//                .build();
//
//        userRepository.save(user);
//        return "Success";
//    }
//    public User getUserByAccountId(String accountId){
//        return userRepository.findByAccountId(accountId).orElseThrow(() -> new IllegalArgumentException("User not found"));
//    }
//}
