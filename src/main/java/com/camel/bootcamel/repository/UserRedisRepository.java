package com.camel.bootcamel.repository;

import com.camel.bootcamel.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRedisRepository extends CrudRepository<User, String> {
}