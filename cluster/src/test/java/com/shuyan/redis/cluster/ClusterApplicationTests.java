package com.shuyan.redis.cluster;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClusterApplicationTests {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void contextLoads() {
        redisTemplate.opsForValue().set("hello","world");
        String name = (String)redisTemplate.opsForValue().get("hello");
        System.out.println(name);
    }
}

