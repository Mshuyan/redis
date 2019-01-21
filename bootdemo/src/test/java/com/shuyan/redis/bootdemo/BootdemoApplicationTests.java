package com.shuyan.redis.bootdemo;

import com.shuyan.redis.bootdemo.dto.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.lang.Nullable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootdemoApplicationTests {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void redisTest(){
        redisTemplate.opsForValue().set("tom",new UserDto("tom","123456"));
        UserDto tom = (UserDto)redisTemplate.opsForValue().get("tom");
        System.out.println(tom);
    }

    @Test
    public void pipelineTest(){
        List<Object> list = redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Nullable
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                for (int i = 0; i < 100; i++) {
                    connection.get("tom".getBytes());
                }
                return null;
            }
        });
        System.out.println(list);
    }
}

