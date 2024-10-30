package com.vnpt.mini_project_java.respository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vnpt.mini_project_java.entity.Users;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    @Query(value = "SELECT * FROM users  WHERE account_name = ?", nativeQuery = true)
    Optional<Users> findByname(String accountName);

    @Query(value = "SELECT * FROM users  WHERE account_name = ?", nativeQuery = true)
    Users findByName(String accountName);

    Optional<Users> findByPhoneAndUserPass(String phone, String userPass);
}
