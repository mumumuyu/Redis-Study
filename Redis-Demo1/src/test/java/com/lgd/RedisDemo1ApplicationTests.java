package com.lgd;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RedisDemo1ApplicationTests {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        redisTemplate.opsForValue().set("lgd","Hello!World!!");
        System.out.println(redisTemplate.opsForValue().get("lgd"));
//        redisTemplate.delete("lgd");
//        System.out.println(redisTemplate.opsForValue().get("lgd"));
    }

}
