package com.camel.bootcamel.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.time.Duration;

@Configuration
public class RedisTemplateStandaloneConfig {

    @Value("${app.redis.host}")
    private String redisHost;

    @Value("${app.redis.port}")
    private Integer redisPort;

    private JedisClientConfiguration getJedisClientConfiguration() {
        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfigurationBuilder = JedisClientConfiguration.builder();
        jedisClientConfigurationBuilder.connectTimeout(Duration.ofSeconds(60));
        jedisClientConfigurationBuilder.readTimeout(Duration.ofSeconds(1));
        return jedisClientConfigurationBuilder.build();
    }

    @Bean
    public JedisConnectionFactory jedisStandaloneConnectionFactory() {
        return new JedisConnectionFactory(getRedisStandaloneConfiguration(), getJedisClientConfiguration());
    }

    private RedisStandaloneConfiguration getRedisStandaloneConfiguration() {
        RedisStandaloneConfiguration redisStandaloneConfig = new RedisStandaloneConfiguration();
        redisStandaloneConfig.setHostName(redisHost);
        redisStandaloneConfig.setPort(redisPort);
        redisStandaloneConfig.setDatabase(0);
        return redisStandaloneConfig;
    }
}
