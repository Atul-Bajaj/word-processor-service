package com.org.service.wordprocessor.config;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimitConfig {

    @Value("${url.extract.hit.rate}")
    private int webHitAllowedPerSecond;

    @Bean
    public RateLimiter getRateLimitor(){
        return RateLimiter.create(webHitAllowedPerSecond);
    }

}
