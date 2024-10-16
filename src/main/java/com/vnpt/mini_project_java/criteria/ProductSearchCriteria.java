package com.vnpt.mini_project_java.criteria;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchCriteria {

    private String productName;

    private String categoryName;

    private String memory;
    
    private Long categoryID;
    
    private Long productID;
    
    private String color;

    private String price;

    private String productCamera;

    private String productScreen;

    private String productBluetooth;

    public ProductSearchCriteria() {
    }

    public ProductSearchCriteria(Long categoryId, Long productID,String categoryName, String memory, String color, String price,
                                 String productCamera, String productScreen, String productBluetooth, String productName) {
        this.productName = productName;
        this.categoryID = categoryId;
        this.productID = productID;
        this.categoryName = categoryName;
        this.memory = memory;
        this.color = color;
        this.price = price;
        this.productCamera = productCamera;
        this.productScreen = productScreen;
        this.productBluetooth = productBluetooth;
    }
}
