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

    @Bean
    public CaffeineCache jwtBlacklistConfig() {
        return new CaffeineCache(JWT_BLACKLIST, Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).build());
    }
}
