package com.vnpt.mini_project_java.dto;

import com.vnpt.mini_project_java.entity.Account;
import com.vnpt.mini_project_java.entity.Cart;
import com.vnpt.mini_project_java.entity.Order;
import com.vnpt.mini_project_java.entity.ProductVote;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Set;

@Data
@Getter
@Setter
public class AccountDTO {
	
    private Long accountID;

    private String accountName;

    private String accountPass;

    private String username;

    private String phoneNumber;

    private String dateOfBirth;

    private String imageBase64;

    private String local;

    private String email;

    private boolean isAdmin;


    public AccountDTO() {
    }

    public AccountDTO(Account account) {
        this.accountID = account.getAccountID();
        this.accountName = account.getAccountName();
        this.accountPass = account.getAccountPass();
        this.dateOfBirth = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(account.getDateOfBirth());
        this.username = account.getUsername();
        this.email = account.getEmail();
        this.phoneNumber = account.getPhoneNumber();
        this.local = account.getLocal();
        this.isAdmin = account.isAdmin();

        String imagePath = "src/main/resources/static/images/" + account.getImage();
        try {
            Path path = Paths.get(imagePath);
            byte[] imageBytes = Files.readAllBytes(path);
            this.imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
