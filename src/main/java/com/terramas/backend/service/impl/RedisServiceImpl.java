package com.terramas.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl {
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public void saveUid(String uid, String email) {
	    redisTemplate.opsForValue().set(uid, email);
	}

	public String findUidByEmail(String email) {
	    return redisTemplate.opsForValue().get(email);
	}
	
	public String findEmailByUid(String uid) {
		return redisTemplate.opsForValue().get(uid);
	}
}
