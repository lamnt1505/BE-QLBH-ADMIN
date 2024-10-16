package com.vnpt.mini_project_java.dto;

import com.vnpt.mini_project_java.entity.Product;

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

@Data
@Getter
@Setter
@ToString
public class ProductDTO {

    private Long id;

    private String name;

    private String description;

    private String date_product;

    private Long categoryID;

    private String categoryname;
    
    private String memory;
    
    private String color;
    
    private Long tradeID;

    private String tradeName;

    private String imageBase64;

    private double price;

    @Transient
    private int amount;

    public ProductDTO() {
    }

    public ProductDTO(Product product){
        this.id = product.getProductID();
        this.name = product.getProductName();
        this.description = product.getDescription();

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