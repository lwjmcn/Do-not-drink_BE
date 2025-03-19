package com.jorupmotte.donotdrink.auth.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {
    public static final String JWT_BLACKLIST = "expired";
    public static final String REACTION = "reaction";

    @Bean
    public CaffeineCache jwtBlacklistConfig() { // key: token, value: "expired"
        return new CaffeineCache(JWT_BLACKLIST, Caffeine
                .newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build());
    }

    @Bean
    public CaffeineCache reactionConfig () { // key: receiverId -> value: Map<ReactionType, AtomicInteger>
        return new CaffeineCache(REACTION, Caffeine
                .newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .removalListener((key, value, cause) ->
                        System.out.println("Cache Removal key: " + key + " value: " + value + " cause: " + cause))
                .build());
    }
}
