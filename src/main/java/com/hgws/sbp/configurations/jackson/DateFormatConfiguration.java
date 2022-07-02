package com.hgws.sbp.configurations.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.server.Encoding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-07-01 16:37
 * @description: TODO
 */
@Configuration
@EnableConfigurationProperties({JacksonProperties.class})
public class DateFormatConfiguration {

    private final JacksonProperties properties;

    public DateFormatConfiguration(JacksonProperties properties) {
        this.properties = properties;
    }

    private static final String TIME_PATTERN = "HH:mm:ss";
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String STANDARD_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Bean
    public ObjectMapper objectMapper()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        // 初始化JavaTimeModule
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        //处理LocalDate
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));

        //处理LocalTime
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));

        //处理LocalDateTime
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(STANDARD_PATTERN);
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));

        //注册时间模块, 支持支持jsr310, 即新的时间类(java.time包下的时间类)
        objectMapper.registerModule(javaTimeModule);

        //注册时间模块, 支持支持jsr310, 即新的时间类(java.time包下的时间类)
        objectMapper.registerModule(javaTimeModule);

        // 标注时区信息
        objectMapper.setTimeZone(properties.getTimeZone());

        // 包含不为null字段
        objectMapper.setSerializationInclusion(properties.getDefaultPropertyInclusion());

        // 在序列化一个空对象时时不抛出异常
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        // 忽略反序列化时在json字符串中存在, 但在java对象中不存在的属性
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        return objectMapper;
    }

}
