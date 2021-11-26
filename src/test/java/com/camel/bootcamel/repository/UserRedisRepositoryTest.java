package com.camel.bootcamel.repository;

import com.camel.bootcamel.model.User;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Log
@SpringBootTest
class UserRedisRepositoryTest {

    @Autowired
    private UserRedisRepository userRedisRepository;

    @BeforeEach
    void clearAllUsers() {
        userRedisRepository.deleteAll();
    }

    @Test
    void testIO() {
        User savedUser = userRedisRepository.save(new User(1, "Ghena"));
        assertEquals("Ghena", savedUser.getName());

        savedUser.setName("Anton");
        User savedUser2 = userRedisRepository.save(savedUser);
        assertEquals("Anton", savedUser2.getName());

        log.info(savedUser.toString());
    }

    @Test
    void testIOList() {
        assertEquals(0, userRedisRepository.count());

        userRedisRepository.save(new User(1, "Ghena"));
        userRedisRepository.save(new User(2, "Anton"));

        assertEquals(2, userRedisRepository.count());
    }
}