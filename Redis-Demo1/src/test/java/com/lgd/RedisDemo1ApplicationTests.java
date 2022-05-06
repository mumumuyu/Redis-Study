package com.lgd;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.util.StopWatch;

import java.util.*;

@SpringBootTest
class RedisDemo1ApplicationTests {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    void contextLoads() {

        Set<String> keys = redisTemplate.keys("*");
        redisTemplate.delete(keys);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("使用pipeline");

        long startTime = System.currentTimeMillis();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        List list = redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                for (int i = 0; i < 100000; i++) {
                    String key = "测试" + String.valueOf(i);
                    redisConnection.hSet(stringRedisSerializer.serialize("lalala1"),stringRedisSerializer.serialize(key),stringRedisSerializer.serialize("666666"));
                }
                return null;
            }
        },stringRedisSerializer);

        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;
        System.out.println(costTime);
        stopWatch.stop();
        Set<String> keys1 = redisTemplate.keys("*");
        redisTemplate.delete(keys1);
        stopWatch.start("不使用pipeline");
        long startTime2 = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            String key = "测试" + String.valueOf(i);
            redisTemplate.opsForHash().put("lalala2",key,"666666");
        }
        long endTime2 = System.currentTimeMillis();
        long costTime2 = endTime2 - startTime2;
        System.out.println(costTime2);
        stopWatch.stop();


//        redisTemplate.opsForHash().put("map1", "k1", "value1");
//        redisTemplate.opsForHash().put("map1", "k2", "value2");
//        //获取map
//        Map map1 = redisTemplate.opsForHash().entries("map1");
//        System.out.println(map1.get("k1"));
//
//        System.out.println(redisTemplate.opsForHash().get("map1","k2"));


//        redisTemplate.opsForValue().set("lgd","Hello!World!!");
//        System.out.println(redisTemplate.opsForValue().get("lgd"));
//        redisTemplate.delete("lgd");
//        System.out.println(redisTemplate.opsForValue().get("lgd"));
    }

}
