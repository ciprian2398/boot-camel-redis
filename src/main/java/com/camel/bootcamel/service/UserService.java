package com.camel.bootcamel.service;

import com.camel.bootcamel.model.User;

import java.util.List;

public interface UserService {

    User createUser(String name);

    User getUser(String userId);

    List<User> getAll();
}
