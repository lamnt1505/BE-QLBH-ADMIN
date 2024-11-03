package com.vnpt.mini_project_java.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Data
public class ProductSearchCriteriaDTO {

    private String productName;

    private String categoryName;

    private String memory;

    private List<Long> categoryID;

    private List<Long> tradeID;

    private List<Long> versionID;

    private List<Long> productDetailID;

    private Long productID;
    
    private String color;

    private String price;

    private String productCamera;

    private String productScreen;

    private String productBluetooth;

    private Set<ProductVersionDTO> productVersions;

    private Set<ProductDetailDTO> productDetails;

    public ProductSearchCriteriaDTO() {
    }

    public ProductSearchCriteriaDTO(List<Long> categoryID,List<Long> versionID, List<Long> productDetailID, List<Long> tradeID, Long productID, String categoryName, String memory, String color, String price,
                                    String productCamera, String productScreen, String productBluetooth, String productName) {
        this.productName = productName;
        this.categoryID = categoryID;
        this.tradeID = tradeID;
        this.productID = productID;
        this.versionID = versionID;
        this.productDetailID = productDetailID;
        this.categoryName = categoryName;
        this.memory = memory;
        this.color = color;
        this.price = price;
        this.productCamera = productCamera;
        this.productScreen = productScreen;
        this.productBluetooth = productBluetooth;
    }
}
