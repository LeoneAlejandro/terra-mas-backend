package com.terramas.backend.domain.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl {
	
	private int expirationTime = 900;
	private TimeUnit timeUnits = TimeUnit.SECONDS;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public void saveUid(String uid, String email) {
	    redisTemplate.opsForValue().set(uid, email);
	    
	    redisTemplate.expire(uid, expirationTime, timeUnits);
	}

	public String findUidByEmail(String email) {
	    return redisTemplate.opsForValue().get(email);
	}
	
	public String findEmailByUid(String uid) {
		return redisTemplate.opsForValue().get(uid);
	}
}
