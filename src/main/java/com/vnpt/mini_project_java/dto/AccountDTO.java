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

    public AccountDTO(Account account,Long accountID, String accountName, String accountPass, String accountStatus,
                      Set<Cart> carts, Set<ProductVote> productVotes, Set<Order> orders, boolean isAdmin, String dateOfBirth,
                      String username, String email, String phoneNumber, String local) {
        this.accountID = accountID;
        this.accountName = accountName;
        this.accountPass = accountPass;
        this.dateOfBirth = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(account.getDateOfBirth());
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.local = local;
        this.isAdmin = isAdmin;
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
