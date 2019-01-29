package com.shuyan.redis.sentinel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SentinelApplicationTests {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void contextLoads() {
    }

    @Test
    public void test(){
        redisTemplate.opsForValue().set("name","tom");
        String name = (String)redisTemplate.opsForValue().get("name");
        System.out.println(name);
    }
}

