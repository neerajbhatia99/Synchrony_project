package com.neeraj.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    public CompletableFuture<Void> saveAsync(String key, Object value, long ttl) {
        return CompletableFuture.runAsync(() -> 
            redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.SECONDS), executorService);
    }

    public CompletableFuture<Object> getAsync(String key) {
        return CompletableFuture.supplyAsync(() -> 
            redisTemplate.opsForValue().get(key), executorService);
    }
}
