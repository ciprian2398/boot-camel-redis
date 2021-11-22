package com.camel.bootcamel.service.impl;

import com.camel.bootcamel.model.User;
import com.camel.bootcamel.service.UserService;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    public User getUser(Long userId) {
        User user = new User();
        user.setName("HisFancyName_" + userId);
        return user;
    }
}
