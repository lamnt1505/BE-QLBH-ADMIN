package com.vnpt.mini_project_java.dto;

import com.vnpt.mini_project_java.entity.Cart;
import com.vnpt.mini_project_java.entity.Order;
import com.vnpt.mini_project_java.entity.ProductVote;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Data
@Getter
@Setter
public class AccountDTO {
	
    private Long accountID;
    private String accountName;
    private String accountPass;
    private boolean isAdmin;


    public AccountDTO() {
    }

    public AccountDTO(Long accountID, String accountName, String accountPass, String accountStatus,
                      Set<Cart> carts, Set<ProductVote> productVotes, Set<Order> orders, boolean isAdmin) {
        this.accountID = accountID;
        this.accountName = accountName;
        this.accountPass = accountPass;
        this.isAdmin = isAdmin;
    }
}
