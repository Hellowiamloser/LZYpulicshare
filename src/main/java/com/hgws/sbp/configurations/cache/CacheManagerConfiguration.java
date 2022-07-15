package com.hgws.sbp.configurations.cache;

import com.hgws.sbp.configurations.cache.writer.DynamicTTLCacheWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.BatchStrategies;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

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
    private CacheProperties cacheProperties;
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
        //创建Redis缓存配置信息
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        //指定缓存过期时间
        if(!ObjectUtils.isEmpty(redisProperties.getTimeToLive()))
            redisCacheConfiguration = redisCacheConfiguration.entryTtl(redisProperties.getTimeToLive());
        //是否缓存空数据
        if(!redisProperties.isCacheNullValues())
            redisCacheConfiguration = redisCacheConfiguration.disableCachingNullValues();
        //设置缓存别名
        if(StringUtils.hasLength(redisProperties.getKeyPrefix()))
            redisCacheConfiguration = redisCacheConfiguration.prefixCacheNameWith(redisProperties.getKeyPrefix());
        //指定key序列化机制
        redisCacheConfiguration = redisCacheConfiguration.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer));
        //指定value序列化机制
        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer));

        return RedisCacheManager
                // 默认时长
                //.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                // 动态时长解决缓存雪崩
                .builder(new DynamicTTLCacheWriter(redisConnectionFactory, BatchStrategies.keys()))
                .cacheDefaults(redisCacheConfiguration)
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
            StringBuilder builder = new StringBuilder(method.getName()+":");
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
