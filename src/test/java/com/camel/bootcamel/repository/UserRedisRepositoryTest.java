//package com.camel.bootcamel.repository;
//
//import com.camel.bootcamel.model.User;
//import io.lettuce.core.RedisClient;
//import io.lettuce.core.RedisURI;
//import io.lettuce.core.api.StatefulRedisConnection;
//import io.lettuce.core.cluster.RedisClusterClient;
//import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
//import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
//import lombok.extern.java.Log;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@Log
//@SpringBootTest
//class UserRedisRepositoryTest {
//
//    @Autowired
//    private UserRedisRepository userRedisRepository;
//
//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;
//
//    @BeforeEach
//    void clearAllUsers() {
//        userRedisRepository.deleteAll();
//    }
//
//    @Test
//    void testIO() {
//        User savedUser = userRedisRepository.save(new User(1, "Ghena"));
//        assertEquals("Ghena", savedUser.getName());
//
//        savedUser.setName("Anton");
//        User savedUser2 = userRedisRepository.save(savedUser);
//        assertEquals("Anton", savedUser2.getName());
//
//        log.info(savedUser.toString());
//    }
//
//    @Test
//    void testIOList() {
//        assertEquals(0, userRedisRepository.count());
//
//        userRedisRepository.save(new User(1, "Ghena"));
//        userRedisRepository.save(new User(2, "Anton"));
//
//        assertEquals(2, userRedisRepository.count());
//    }
//
//    @Test
//    void testIOee() {
//        RedisClient redisClient = RedisClient.create("redis://localhost:7001/");
//        StatefulRedisConnection<String, String> connection = redisClient.connect();
//        connection.sync().set("xxx", "uuuuu");
//        String a = connection.sync().get("xxx");
//        log.info(a);
//    }
//
//    @Test
//    void testIOeeaa() {
//        RedisURI redisUri = RedisURI.Builder.redis("localhost").withPort(9011).build();
//        RedisClusterClient clusterClient = RedisClusterClient.create(redisUri);
//        StatefulRedisClusterConnection<String, String> connection = clusterClient.connect();
//        RedisAdvancedClusterCommands<String, String> syncCommands = connection.sync();
//
//        syncCommands.set("cap1", "val1");
//        syncCommands.set("cap1", "val2");
//        String val = syncCommands.get("cap1");
//        log.info(val);
//    }
//}