package com.example.stock.repository;

import static org.springframework.cache.interceptor.SimpleKeyGenerator.*;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisLockRepository {

	private final RedisTemplate<String, String> redisTemplate;

	public boolean lock(Long key) {
		return redisTemplate
			.opsForValue()
			.setIfAbsent(generateKey(key), "lock", Duration.ofMillis(3_000));
	}

	public boolean unlock(Long key) {
		return redisTemplate.delete(generateKey(key));
	}

	private String generateKey(Long key) {
		return key.toString();
	}
}
