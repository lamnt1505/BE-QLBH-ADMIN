package com.vnpt.mini_project_java.service.users;

import com.vnpt.mini_project_java.entity.Account;
import com.vnpt.mini_project_java.entity.Users;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<Users> findByname(String accountName);

    List<Users> findAll();

    Users findByName(String accountName);

    <S extends Users> S save(S entity);

    Optional<Users> findById(Long aLong);
}
