package com.vnpt.mini_project_java.service.account;

import com.vnpt.mini_project_java.dto.AccountDTO;
import com.vnpt.mini_project_java.dto.LoginDTO;
import com.vnpt.mini_project_java.entity.Account;
import com.vnpt.mini_project_java.response.LoginMesage;

import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    List<Account> findAll();

    @Query(value = "SELECT * FROM account  WHERE account_phone = ?", nativeQuery = true)
    Optional<Account> findByphone(String phone);

    Account findByAccountName(String accountName);

    Optional<Account> findByname(String accountName);

    Optional<Account> findById(Long accountID);
    
    String addAccount(AccountDTO accountDTO);
    
    LoginMesage loginAccount(LoginDTO loginDTO);


}
