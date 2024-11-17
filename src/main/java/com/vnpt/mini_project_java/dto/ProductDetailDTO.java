package com.vnpt.mini_project_java.dto;

import com.vnpt.mini_project_java.entity.ProductDetail;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ProductDetailDTO {

    private Long productDetailID;

    private String productCamera;

    private String productWifi;

    private String productScreen;

    private String productBluetooth;

    private Long productID;

    public ProductDetailDTO() {
    }

    public ProductDetailDTO(ProductDetail productDetail){
        this.productDetailID = productDetail.getProductDetailID();
        this.productCamera = productDetail.getProductCamera();
        this.productWifi = productDetail.getProductWifi();
        this.productScreen = productDetail.getProductScreen();
        this.productBluetooth = productDetail.getProductBluetooth();
        this.productID = productDetail.getProduct().getProductID();
        //System.out.println("Mapping ProductVersionDTO: versionID=" + productDetailID + ", memory=" + productCamera + ", color=" + productWifi);
    }

}
