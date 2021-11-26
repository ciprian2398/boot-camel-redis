package com.camel.bootcamel.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.UUID;

@Data
@RedisHash("User")
public class User implements Serializable {

    @Id
    private String id;

    private String name;

    public User() {
        this.id = UUID.randomUUID().toString();
    }
}