package com.vnpt.mini_project_java.respository;

import com.vnpt.mini_project_java.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {// chứa câu lệnh SQL sử Jpa reposotory 
    //Account findByAccountName (String accountName);

    @Query(value = "SELECT * FROM account  WHERE account_name = ?", nativeQuery = true)
    Optional<Account> findByname(String accountName);

    Optional<Account> findByEmail(String email);

    @Query(value = "SELECT * FROM account  WHERE account_phone = ?", nativeQuery = true)
    Optional<Account> findByphone(String phone);


    Optional<Account> findOneByAccountNameAndAccountPass(String accountName, String accountPass);
    
    Optional<Account> findByPhoneNumberAndAccountPass(String phoneNumber, String accountPass);
    
    Account findByAccountName(String accountName);
}
