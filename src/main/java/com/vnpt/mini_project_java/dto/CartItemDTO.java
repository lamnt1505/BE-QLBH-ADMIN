package com.vnpt.mini_project_java.dto;

import com.vnpt.mini_project_java.entity.Product;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Data
@Getter
@Setter
@ToString
public class CartItemDTO {
    private Long productID;
    private String name;
    private Double price;
    private Integer amount;
    private String imageBase64;

    public CartItemDTO(Product product) {
        this.productID = product.getProductID();
        this.name = product.getProductName();
        this.price = product.getPrice();
        this.amount = product.getAmount();

        String imagePath = "src/main/resources/static/images/" + product.getImage();
        try {
            byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
            this.imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            this.imageBase64 = "";
        }
    }
}
