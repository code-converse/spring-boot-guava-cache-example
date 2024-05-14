package com.codeconverse.GuavaExample.config;

import com.codeconverse.GuavaExample.model.Product;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Configuration class for setting up Guava cache in Spring Boot.
 */
@Configuration
public class CacheConfig {

    /**
     * Creates a Guava cache for storing product recommendations.
     * The cache will expire entries 2 minutes after they are written
     * and will have a maximum size of 4 entries.
     *
     * @return the configured cache
     */
    @Bean
    public Cache<String, List<Product>> productCache() {
        return CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.MINUTES)
                .maximumSize(4)
                .build();
    }
}
