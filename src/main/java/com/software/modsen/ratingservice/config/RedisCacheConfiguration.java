package com.software.modsen.ratingservice.config;

import com.software.modsen.ratingservice.model.DriverRating;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@Configuration
public class RedisCacheConfiguration {

    @Bean
    public RedisTemplate<Long, DriverRating> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Long, DriverRating> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        org.springframework.data.redis.cache.RedisCacheConfiguration cacheConfig = org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(300));

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(cacheConfig)
                .build();
    }
}
