package com.shuyan.redis.cluster.business.controller;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author will
 */
@RestController
public class TestController {

    @GetMapping("/find")
    @Cacheable(cacheNames = "test",key = "'find'")
    public String find(){
        System.out.println("没打印这句话就是走缓存了");
        return "find";
    }

    @PutMapping("/put")
    @CachePut(cacheNames = "cacheNames",key = "#id",condition = "#id > 0",unless = "#result != null")
    public String put(Long id){
        return "put";
    }

    @DeleteMapping("/delete")
    @CacheEvict(cacheNames = "cacheNames",key = "'find'")
    public String delete(){
        return "delete";
    }

}
