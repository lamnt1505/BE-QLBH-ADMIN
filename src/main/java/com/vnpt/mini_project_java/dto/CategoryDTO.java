package com.vnpt.mini_project_java.dto;

import com.vnpt.mini_project_java.entity.Category;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class CategoryDTO {
    private Long id;

    private String name;

    public CategoryDTO() {
    }

    public CategoryDTO(Category category){
        this.id = category.getCategoryID();
        this.name = category.getCategoryName();
    }
}
