package com.camel.bootcamel.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.time.Duration;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Autowired
    private ClusterConfigurationProperties clusterProperties;

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisClusterConnectionFactory());
        return template;
    }

    @Bean
    public JedisConnectionFactory jedisClusterConnectionFactory() {
        return new JedisConnectionFactory(getRedisClusterConfiguration(), getJedisClientConfiguration());
    }

    private RedisClusterConfiguration getRedisClusterConfiguration() {
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(clusterProperties.getNodes());
        redisClusterConfiguration.setMaxRedirects(10);
        return redisClusterConfiguration;
    }

    private JedisClientConfiguration getJedisClientConfiguration() {
        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfigurationBuilder = JedisClientConfiguration.builder();
        jedisClientConfigurationBuilder.connectTimeout(Duration.ofSeconds(60));
        jedisClientConfigurationBuilder.readTimeout(Duration.ofSeconds(10));
        return jedisClientConfigurationBuilder.build();
    }
}
