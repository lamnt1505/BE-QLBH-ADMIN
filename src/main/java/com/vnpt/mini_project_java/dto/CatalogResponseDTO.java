package com.vnpt.mini_project_java.dto;

public class CatalogResponseDTO {
    private String productName;
    private Double price;
    private String imageBase64;

    public CatalogResponseDTO() {}

    public CatalogResponseDTO(String productName, Double price, String imageBase64) {
        this.productName = productName;
        this.price = price;
        this.imageBase64 = imageBase64;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
