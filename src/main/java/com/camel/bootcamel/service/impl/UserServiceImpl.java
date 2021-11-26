package com.camel.bootcamel.service.impl;

import com.camel.bootcamel.model.User;
import com.camel.bootcamel.repository.UserRedisRepository;
import com.camel.bootcamel.service.UserService;
import com.google.common.collect.ImmutableList;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRedisRepository userRedisRepository;

    public User createUser(String name) {
        log.info("user created");
        User user = new User();
        user.setName(name);
        return userRedisRepository.save(user);
    }

    public User getUser(String userId) {
        log.info("user retrieved");
        return userRedisRepository.findById(userId).orElse(new User());
    }

    @Override
    public List<User> getAll() {
        log.info("all users retrieved");
        return ImmutableList.copyOf(userRedisRepository.findAll());
    }
}
