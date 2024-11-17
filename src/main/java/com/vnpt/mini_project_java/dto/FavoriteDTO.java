package com.vnpt.mini_project_java.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class FavoriteDTO {

    private Long favoriteId;

    private Long id;

    private String name;

    private String imageBase64;

    private double price;

    private String dateProduct;

    public FavoriteDTO( Long id, String name, String imageBase64, double price) {
        this.id = id;
        this.name = name;
        this.imageBase64 = imageBase64;
        this.price = price;
    }
}
