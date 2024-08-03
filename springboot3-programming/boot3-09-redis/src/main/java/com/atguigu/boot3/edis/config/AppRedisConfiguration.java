package com.atguigu.boot3.edis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;


/**
 * @author Roger
 * @Description
 * @create 2024-07-31
 */
@Configuration
public class AppRedisConfiguration {

    /**
     * 允許 Object 類型的 key-value，都可以被轉為 json 進行儲存。
     * @param redisConnectionFactory 自動配置好了連接工廠。
     * @return
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        // 把物件轉換成 json 字符串的序列化工具
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}
