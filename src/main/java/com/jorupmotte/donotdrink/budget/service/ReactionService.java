package com.jorupmotte.donotdrink.budget.service;

import com.jorupmotte.donotdrink.auth.config.CacheConfig;
import com.jorupmotte.donotdrink.budget.dto.request.ReactToRequestDto;
import com.jorupmotte.donotdrink.budget.dto.response.ReactToResponseDto;
import com.jorupmotte.donotdrink.budget.dto.response.ReactionCurrentResponseDto;
import com.jorupmotte.donotdrink.budget.model.Budget;
import com.jorupmotte.donotdrink.budget.model.Reaction;
import com.jorupmotte.donotdrink.budget.repository.ReactionRepository;
import com.jorupmotte.donotdrink.common.dto.response.ResponseDto;
import com.jorupmotte.donotdrink.common.type.ReactionType;
import com.jorupmotte.donotdrink.friend.service.FriendService;
import com.jorupmotte.donotdrink.user.model.User;
import com.jorupmotte.donotdrink.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jorupmotte.donotdrink.user.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReactionService implements IReactionService {
    private final UserService userService;
    private final BudgetService budgetService;
    private final UserRepository userRepository;
    private final ReactionRepository reactionRepository;
    private final CacheManager cacheManager;
    private final SseEmitterService sseEmitterService;
    private final FriendService friendService;

    @Override
    public ResponseEntity<? super ReactionCurrentResponseDto> getMyCurrentReactionsFromDb() {
        User userMe = userService.getUserFromSecurityContext();
        if (userMe == null) {
            return ResponseDto.authorizationFail();
        }

        // 현재 budget 조회
        Optional<Budget> budgetOptional = budgetService.getCurrentBudget(userMe.getId());
        if (budgetOptional.isEmpty()) {
            return ReactionCurrentResponseDto.noBudget();
        }

        List<Reaction> reactionList = reactionRepository.findAllByBudget_Id(budgetOptional.get().getId());

        return ReactionCurrentResponseDto.success(reactionList);
    }

    @Override
    public ResponseEntity<? super ReactToResponseDto> reactTo(Long receiverId, ReactToRequestDto requestDto) {
        User userMe = userService.getUserFromSecurityContext();
        if (userMe == null) {
            return ResponseDto.authorizationFail();
        }
        
        if(!friendService.isFriend(userMe.getId(), receiverId)){
            return ReactToResponseDto.notFriend();
        }

        // cache에 업데이트
        CaffeineCache reactionCache = (CaffeineCache) cacheManager.getCache(CacheConfig.REACTION);

        Map<ReactionType, AtomicInteger> reactionMap = reactionCache.get(receiverId, Map.class);
        if (reactionMap == null) {            // 없으면 new reactionMap 생성
            reactionMap = Arrays.stream(ReactionType.values()).map(type -> Map.entry(type, new AtomicInteger(0)))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        reactionMap.get(requestDto.getReactionType()).addAndGet(requestDto.getCount()); // increment
        reactionCache.put(receiverId, reactionMap);

        // sse 전송
        sseEmitterService.sendReactionUpdate(receiverId, reactionMap);

        return ReactToResponseDto.success();
    }

    @Scheduled(fixedRate = 1000) // 1초마다 실행
    public void processReactionCache() {
        CaffeineCache reactionCache = (CaffeineCache) cacheManager.getCache(CacheConfig.REACTION);
        if (reactionCache == null) return;

        Map<Long, Map<ReactionType, AtomicInteger>> reactionNativeCache = (Map<Long, Map<ReactionType, AtomicInteger>>) reactionCache.getNativeCache();
        reactionNativeCache.forEach((receiverId, reactionMap) -> {

            // 친구 budget 조회
            Optional<Budget> budgetOptional = budgetService.getCurrentBudget(receiverId);
            if (budgetOptional.isEmpty()) {
                System.out.println("processReactionCache: budget not found");
                return;
            }
            Optional<User> userOptional = userRepository.findById(receiverId);
            if (userOptional.isEmpty()) {
                System.out.println("processReactionCache: user not found");
                return;
            }

            reactionMap.forEach((reactionType, count) -> {
                if (count.get() != 0) {
                    // DB에 반영 (좋아요 1개만 남기고 처리)
                    reactionRepository.save(Reaction.builder()
                            .budget(budgetOptional.get())
                            .user(userOptional.get())
                            .reactionType(reactionType)
                            .count(count.get())
                            .build());
                }
            });
        });
        reactionCache.clear();
    }
}
