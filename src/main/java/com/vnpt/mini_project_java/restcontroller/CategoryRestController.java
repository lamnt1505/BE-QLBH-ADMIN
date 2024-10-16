package com.vnpt.mini_project_java.restcontroller;

import com.vnpt.mini_project_java.dto.CategoryDTO;
import com.vnpt.mini_project_java.dto.ProductDTO;
import com.vnpt.mini_project_java.entity.Category;
import com.vnpt.mini_project_java.service.category.CategoryService;
import com.vnpt.mini_project_java.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryRestController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<?> listCategory(){
        return ResponseEntity.ok(this.categoryService.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity<CategoryDTO> createCategory(CategoryDTO dto) {
        try {
            CategoryDTO createdCategory = categoryService.saveDTO(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/Listgetall")
    public ResponseEntity<List<CategoryDTO>> getList(){
        List<CategoryDTO> categoryDTOS = categoryService.getAllCategoryDTO();
        return ResponseEntity.ok(categoryDTOS);
    }

    @GetMapping("/{id}/get")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable(name = "id") Long id){
        Category category = categoryService.getCategoryById(id);

        CategoryDTO categoryResponse = new CategoryDTO(category);

        return ResponseEntity.ok().body(categoryResponse);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable long id,
                                                      CategoryDTO categoryDTO){
        try {
            Category category = categoryService.updateCategory(id, categoryDTO);
            CategoryDTO updateDTO = new CategoryDTO(category);
            return ResponseEntity.ok(updateDTO);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping ("/{id}/delete")
    public ResponseEntity<String> deleteCategory(@PathVariable long id){
        try {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok().body("{\"status\":\"success\"}");
        }catch (EntityNotFoundException ex){
            System.out.println("Error" + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/import")
    public ResponseEntity<String> importCategory(@RequestParam("file") MultipartFile file) {
        try {
            List<CategoryDTO> categorys = ExcelUtil.readCategoryFromExcel(file);
            categoryService.importCategorysFromExcel(categorys);
            return ResponseEntity.ok("Import successful");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error importing file");
        }
    }
    
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadExcelProductTemplate(){
        try{
            InputStream inputStream = getClass().getResourceAsStream("/templates/API-CATEGORY.xlsx");
            InputStreamResource resource = new InputStreamResource(inputStream);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=API-CATEGORY.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
