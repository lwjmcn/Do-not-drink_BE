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

    public SseEmitter reactionSubscribe(Long receiverId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        reactionEmitters.put(receiverId, emitter);

        emitter.onCompletion(() -> reactionEmitters.remove(receiverId));
        emitter.onTimeout(() -> reactionEmitters.remove(receiverId));
        emitter.onError((e) -> {
            sendError(receiverId, e.getMessage());
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

    public void sendError(Long receiverid, String errorMessage) {
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
}
