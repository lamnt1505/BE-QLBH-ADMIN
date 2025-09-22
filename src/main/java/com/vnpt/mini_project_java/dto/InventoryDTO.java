package com.vnpt.mini_project_java.dto;

import com.vnpt.mini_project_java.entity.Inventory;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class InventoryDTO {
    private Long branchID;
    private String branchName;
    private Long productID;
    private String productName;
    private int quantity;

    public InventoryDTO(Inventory inventory) {
        this.branchID = inventory.getBranch().getBranchID();
        this.branchName = inventory.getBranch().getBranchName();
        this.productID = inventory.getProduct().getProductID();
        this.productName = inventory.getProduct().getProductName();
        this.quantity = inventory.getQuantity();
    }
}
