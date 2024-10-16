package com.vnpt.mini_project_java.dto;

import com.vnpt.mini_project_java.entity.Product;

import com.vnpt.mini_project_java.entity.ProductDetail;
import com.vnpt.mini_project_java.entity.ProductVersion;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.Transient;
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
@ToString
public class CompareProductDTO {

    private Long id;

    private String name;

    private String description;

    private String date_product;

    private Long categoryID;

    private String categoryname;

    private Long tradeID;

    private String tradeName;

    private String imageBase64;

    private double price;

    private String memory;

    private String color;

    private String productCamera;

    private String productScreen;

    private String productBluetooth;

    private String productWifi;

    private Set<ProductVersion> productVersions;

    private Set<ProductDetail> productDetails;

    @Transient
    private int amount;

    public CompareProductDTO() {
    }

    public CompareProductDTO(Product product){
        this.id = product.getProductID();
        this.name = product.getProductName();
        this.description = product.getDescription();

        this.memory = product.getProductVersions().stream().findFirst().
                map(ProductVersion::getMemory).orElse(null);
        this.color = product.getProductVersions().stream().findFirst().
                map(ProductVersion::getColor).orElse(null);
        this.productCamera = product.getProductDetails().stream().findFirst().
                map(ProductDetail::getProductCamera).orElse(null);
        this.productScreen = product.getProductDetails().stream().findFirst().
                map(ProductDetail::getProductScreen).orElse(null);
        this.productBluetooth = product.getProductDetails().stream().findFirst().
                map(ProductDetail::getProductBluetooth).orElse(null);
        this.productWifi = product.getProductDetails().stream().findFirst().
                map(ProductDetail::getProductWifi).orElse(null);

        this.date_product = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(product.getDateProduct());
        this.price = product.getPrice();
        this.amount = product.getAmount();

        if(product.getTrademark() != null){
            this.tradeName = product.getTrademark().getTradeName();
            this.tradeID = product.getTrademark().getTradeID();
        }

        if(product.getCategory() != null){
            this.categoryname = product.getCategory().getCategoryName();
            this.categoryID = product.getCategory().getCategoryID();
        }

        String imagePath = "src/main/resources/static/images/" + product.getImage();
        try {
            Path path = Paths.get(imagePath);
            byte[] imageBytes = Files.readAllBytes(path);
            this.imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}