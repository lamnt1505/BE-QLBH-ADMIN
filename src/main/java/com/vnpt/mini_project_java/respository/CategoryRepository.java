package com.vnpt.mini_project_java.respository;

import com.vnpt.mini_project_java.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    boolean existsByCategoryNameIgnoreCase(String categoryName);
}
