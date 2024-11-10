package com.vnpt.mini_project_java.service.category;

import com.vnpt.mini_project_java.dto.CategoryDTO;
import com.vnpt.mini_project_java.entity.Category;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface CategoryService {


    List<Category> findAll();

    void deleteById(Long aLong);

    List<CategoryDTO> getAllCategoryDTO();

    Page<CategoryDTO> getPaginatedCategorys(Pageable pageable);

    Category getCategoryById(long id);

    Category updateCategory(long categoryID, CategoryDTO dto);

    CategoryDTO saveDTO(CategoryDTO dto);

    void deleteCategoryById(long id);

    void importCategorysFromExcel(List<CategoryDTO> categoryDTOs);
}
