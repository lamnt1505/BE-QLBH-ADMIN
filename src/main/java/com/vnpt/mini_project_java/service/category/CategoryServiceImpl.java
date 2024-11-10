package com.vnpt.mini_project_java.service.category;

import com.vnpt.mini_project_java.dto.CategoryDTO;
import com.vnpt.mini_project_java.entity.Category;
import com.vnpt.mini_project_java.respository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void deleteById(Long aLong) {
        categoryRepository.deleteById(aLong);
    }

    @Override
    public List<CategoryDTO> getAllCategoryDTO(){
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryDTO::new).collect(Collectors.toList());
    }

    @Override
    public Page<CategoryDTO> getPaginatedCategorys(Pageable pageable) {
        // Fetch the paginated products
        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        // Convert the Page<Product> to Page<ProductDTO>
        List<CategoryDTO> categoryDTOS = categoryPage.getContent().stream()
                .map(CategoryDTO::new) // Use the ProductDTO constructor to map Product to ProductDTO
                .collect(Collectors.toList());

        // Return a new Page<ProductDTO>
        return new PageImpl<>(categoryDTOS, pageable, categoryPage.getTotalElements());
    }

    @Override
    public Category getCategoryById(long id){
        Optional<Category> result = categoryRepository.findById(id);
        if (result.isPresent()){
            return result.get();
        }else {
            throw new RuntimeException("Category not found with Id:" + id);
        }
    }

    @Override
    public Category updateCategory(long categoryID, CategoryDTO dto) {

        Optional<Category> optionalCategory = categoryRepository.findById(categoryID);
        if (!optionalCategory.isPresent()) {
            throw new RuntimeException("Category with ID " + categoryID + " not found");
        }
        Category category = optionalCategory.get();
        category.setCategoryName(dto.getName());

        return categoryRepository.save(category);
    }

    @Override
    public CategoryDTO saveDTO(CategoryDTO dto) {
        Category category = new Category();
        category.setCategoryName(dto.getName());

        Category savedCategory = categoryRepository.save(category);
        return new CategoryDTO(savedCategory);
    }

    @Override
    public void deleteCategoryById(long id){
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Category not found with id: " + id));
        categoryRepository.deleteById(id);
    }

    @Override
    public void importCategorysFromExcel(List<CategoryDTO> categoryDTOs){
        List<Category> categories = new ArrayList<>();
        for (CategoryDTO categoryDTO : categoryDTOs){
            Category category = new Category();
            category.setCategoryName(categoryDTO.getName());

            categories.add(category);
        }
        categoryRepository.saveAll(categories);
    }
}
