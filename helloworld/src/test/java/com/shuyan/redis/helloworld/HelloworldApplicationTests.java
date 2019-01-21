package com.shuyan.redis.helloworld;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloworldApplicationTests {
    @Test
    public void contextLoads() {
    }

    @Test
    public void test(){
        // 创建连接
        Jedis jedis = new Jedis("127.0.0.1",6380);
        // 执行命令
        Set<String> keys = jedis.keys("*");
        System.out.println(keys);
    }
}

