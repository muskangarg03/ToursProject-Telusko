package com.tours.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;

import com.tours.Entities.Tour;
import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Bean
    public RedisTemplate<String, Tour> redisTemplate(RedisConnectionFactory connectionFactory) {
        logger.info("Initializing RedisTemplate for Tour entity...");

        RedisTemplate<String, Tour> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Configure key serializer
        template.setKeySerializer(new StringRedisSerializer());

        // Configure value serializer for serializing Tour objects to JSON
        Jackson2JsonRedisSerializer<Tour> serializer = new Jackson2JsonRedisSerializer<>(Tour.class);
        template.setValueSerializer(serializer);

        logger.info("RedisTemplate for Tour entity successfully initialized.");
        return template;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        logger.info("Configuring CacheManager with Redis connection factory...");

        // Set default cache configuration with TTL and disable null value caching
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1))  // Cache expiration time
                .disableCachingNullValues();

        RedisCacheManager cacheManager = RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .build();

        logger.info("CacheManager successfully configured for Redis.");
        return cacheManager;
    }
}
