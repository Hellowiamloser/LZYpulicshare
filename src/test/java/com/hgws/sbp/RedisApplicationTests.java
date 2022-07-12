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
        //ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        //valueOperations.set("user2", "张津帆2");

        /**
         * redis是一款优秀的内存数据库 memcached
         * string(字符, 数字, 布尔, 文件...)    key-value
         *  首先把内容转为二进制存储
         * hash key-(key-value)
         * list [0, 1, 2, 3]
         * set [无序]
         * zset: sorted set
         * stream 5.0+
         * hyperloglog
         * ... ...
         */
        //redisTemplate.opsForValue();
        //redisTemplate.opsForHash();
        //redisTemplate.opsForList();
        //redisTemplate.opsForSet();
        //redisTemplate.opsForZSet();
        //redisTemplate.opsForStream();
        //redisTemplate.opsForHyperLogLog();

        //ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();
        //valueOperations.set("user", "张津帆");

        //Object user = valueOperations.get("user");
        //System.out.println("user:"+user);
    }

}
