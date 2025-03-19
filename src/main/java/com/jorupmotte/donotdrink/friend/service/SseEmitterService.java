package com.jorupmotte.donotdrink.friend.service;

import com.jorupmotte.donotdrink.common.type.FriendStatusType;
import com.jorupmotte.donotdrink.common.type.ReactionType;
import com.jorupmotte.donotdrink.friend.model.FriendRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class SseEmitterService {
    // userId마다 SseEmitter
    private final Map<Long, SseEmitter> reactionEmitters = new ConcurrentHashMap<>();
    private final Map<Long, SseEmitter> friendRequestEmitters = new ConcurrentHashMap<>();

    // receiverId: 리액션을 받는 유저의 ID, 해당 유저가 리액션을 얼마나 받는지를 구독하려는 것임
    public SseEmitter reactionSubscribe(Long receiverId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        reactionEmitters.put(receiverId, emitter);

        emitter.onCompletion(() -> reactionEmitters.remove(receiverId));
        emitter.onTimeout(() -> reactionEmitters.remove(receiverId));
        emitter.onError((e) -> {
            sendReactionError(receiverId, e.getMessage());
            reactionEmitters.remove(receiverId);
        });

        return emitter;
    }

    public void sendReactionUpdate(Long receiverId, Map<ReactionType, AtomicInteger> reactionMap) {
        SseEmitter emitter = reactionEmitters.get(receiverId);
        if (emitter != null) {
            try {
                // Atomic Integer -> Integer로 변환해서 전송
                emitter.send(SseEmitter.event().data(reactionMap.entrySet().stream().collect(Collectors.toMap(
                        Map.Entry::getKey, e->e.getValue().get()))));
            } catch (IOException e) {
                reactionEmitters.remove(receiverId);
            }
        }
    }

    public void sendReactionError(Long receiverid, String errorMessage) {
        SseEmitter emitter = reactionEmitters.get(receiverid);
        if (emitter == null) {
            return;
        }
        try {
            emitter.send("error:" + errorMessage);
        } catch (IOException e) {
            reactionEmitters.remove(receiverid);
        }
    }

    // userId: 친구 요청을 받는 유저의 ID, 해당 유저의 알림을 위함
    public SseEmitter friendRequestSubscribe(Long userId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        friendRequestEmitters.put(userId, emitter);

        emitter.onCompletion(() -> friendRequestEmitters.remove(userId));
        emitter.onTimeout(() -> friendRequestEmitters.remove(userId));
        emitter.onError((e) -> {
            sendFriendRequestError(userId, e.getMessage());
            friendRequestEmitters.remove(userId);
        });

        return emitter;
    }

    // 새로운 친구 요청 알림
    public void sendFriendRequestNotification(Long userId, String nickname) {
        SseEmitter emitter = friendRequestEmitters.get(userId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().data(nickname+" 님이 친구 요청을 보냈습니다."));
            } catch (IOException e) {
                friendRequestEmitters.remove(userId);
            }
        }
    }

    // 친구 요청 상태 변경 알림
    public void sendFriendRequestUpdate(Long userId, String nickname, FriendStatusType newStatus) {
        SseEmitter emitter = friendRequestEmitters.get(userId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().data(Map.of("nickname", nickname, "newStatus", newStatus)));
            } catch (IOException e) {
                friendRequestEmitters.remove(userId);
            }
        }
    }

    public void sendFriendRequestError(Long receiverid, String errorMessage) {
        SseEmitter emitter = friendRequestEmitters.get(receiverid);
        if (emitter == null) {
            return;
        }
        try {
            emitter.send("error:" + errorMessage);
        } catch (IOException e) {
            friendRequestEmitters.remove(receiverid);
        }
    }

}
