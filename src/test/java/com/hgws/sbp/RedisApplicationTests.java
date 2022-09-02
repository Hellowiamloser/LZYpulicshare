package com.hgws.sbp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
class RedisApplicationTests {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void contextLoads() {
        //获取redis数据类型操作接口
        //redisTemplate.opsForValue();
        //redisTemplate.opsForHash();
        //redisTemplate.opsForList();
        //redisTemplate.opsForSet();
        //redisTemplate.opsForZSet();
        //redisTemplate.opsForStream();
        //redisTemplate.opsForHyperLogLog();

        /*
         * 测试redis接口
         */
        //ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();
        //valueOperations.set("user", "admin");

        //Object user = valueOperations.get("user");
        //System.out.println("user:"+user);
    }

}
