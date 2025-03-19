package com.jorupmotte.donotdrink.budget.service;

import com.jorupmotte.donotdrink.common.type.ReactionType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class SseEmitterService {
    private final Map<Long, SseEmitter> reactionEmitters = new ConcurrentHashMap<>();

    public SseEmitter reactionSubscribe(Long userId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        reactionEmitters.put(userId, emitter);

        emitter.onCompletion(() -> reactionEmitters.remove(userId));
        emitter.onTimeout(() -> reactionEmitters.remove(userId));

        return emitter;
    }

    public void sendReactionUpdate(Long userId, Map<ReactionType, AtomicInteger> reactionMap) {
        SseEmitter emitter = reactionEmitters.get(userId);
        if (emitter != null) {
            try {
                // Atomic Integer -> Integer로 변환해서 전송
                emitter.send(SseEmitter.event().data(reactionMap.entrySet().stream().collect(Collectors.toMap(
                        Map.Entry::getKey, e->e.getValue().get()))));
            } catch (IOException e) {
                reactionEmitters.remove(userId);
            }
        }
    }
}
