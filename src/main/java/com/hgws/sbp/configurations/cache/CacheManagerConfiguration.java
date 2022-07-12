package com.hgws.sbp.configurations.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.Objects;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro.git
 * @datetime 2022-07-12 14:47
 * @description: 缓存管理配置
 */
@Slf4j
@Configuration
@EnableCaching
public class CacheManagerConfiguration extends CachingConfigurerSupport {

    @Autowired
    private RedisSerializer<String> keySerializer;
    @Autowired
    private RedisSerializer<Object> valueSerializer;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 缓存管理器
     * @return RedisCacheManager
     */
    @Override
    public CacheManager cacheManager() {
        //redis缓存配置
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                // 缓存存储时长
                .entryTtl(Duration.ofMinutes(10))
                // 禁止缓存null
                //.disableCachingNullValues()
                // 指定缓存key序列化器
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer))
                // 指定缓存value序列化器
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer));
        return RedisCacheManager
                // 注入redis连接池
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                // 注入redis默认配置
                .cacheDefaults(redisCacheConfiguration)
                // 完成RedisCacheManager构建
                .build();
    }

    /**
     * 缓存解析器
     * @return SimpleCacheResolver
     */
    @Override
    public CacheResolver cacheResolver() {
        return new SimpleCacheResolver(Objects.requireNonNull(cacheManager()));
    }

    /**
     * 缓存key生成器
     * @return SimpleKeyGenerator
     */
    @Override
    public KeyGenerator keyGenerator() {
        /*
         * target: 目标对象
         * method: 拦截方法
         * params: 方法参数
         */
        return (target, method, params) -> {
            StringBuilder builder = new StringBuilder(method.getName());
            for (Object param:params) {
                builder.append(param).append(":");
            }
            return builder.toString();
        };
    }

    /**
     * 缓存错误处理器
     * redis数据操作异常处理 这里的处理：在日志中打印出错误信息，但是放行保证redis服务器出现连接等问题的时候不影响程序的正常运行，使得能够出问题时不用缓存
     * @return CacheErrorHandler
     */
    @Override
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {
            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
                RedisErrorException(exception, key);
            }
            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
                RedisErrorException(exception, key);
            }
            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
                RedisErrorException(exception, key);
            }
            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                RedisErrorException(exception, null);
            }
        };
    }

    protected void RedisErrorException(Exception exception, Object key){
        log.error("redis异常：key=[{}]", key, exception);
    }

}
