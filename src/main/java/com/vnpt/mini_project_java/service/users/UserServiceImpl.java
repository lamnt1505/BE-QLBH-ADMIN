package com.vnpt.mini_project_java.service.users;

import com.vnpt.mini_project_java.entity.Users;
import com.vnpt.mini_project_java.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<Users> findByname(String accountName) {
        return userRepository.findByname(accountName);
    }

    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Users findByName(String accountName) {
        return userRepository.findByName(accountName);
    }

    @Override
    public <S extends Users> S save(S entity) {
        return userRepository.save(entity);
    }

    @Override
    public Optional<Users> findById(Long aLong) {
        return userRepository.findById(aLong);
    }

}
