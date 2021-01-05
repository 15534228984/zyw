package com.baizhi;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
public class ElTest {

        @Autowired
        RedisTemplate redisTemplate;

        @Test
        public void inserts(){

            ValueOperations opsForValue = redisTemplate.opsForValue();
            //设置值
            opsForValue.set("names","zhangsan");

            opsForValue.get("age");
        }
}
