package com.shuyan.redis.cluster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author will
 */
@SpringBootApplication
public class ClusterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClusterApplication.class, args);
    }
}

