package com.vnpt.mini_project_java.respository;

import com.vnpt.mini_project_java.entity.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>{

    @Query(value = "select * from product where category_id = ?", nativeQuery = true)
    List<Product> findByCategoryId(long categoryID);

    @Query(value = "SELECT * FROM product WHERE category_id = ? LIMIT 4;",nativeQuery = true)
    List<Product> showListProductByIdCategory(long categoryID);

    @Query(value = "select * from product where category_id = ?", nativeQuery = true)
    List<Product> showListProductByIdCategoryFilter(long categoryID);

    @Query(value = "SELECT * FROM product ORDER BY product_id DESC", nativeQuery = true)
    List<Product> listProductNewBest();

    @Query(value = "SELECT * FROM product ORDER BY price desc", nativeQuery = true)
    List<Product> listProductPriceDesc();

    @Query(value = "SELECT * FROM product ORDER BY price asc", nativeQuery = true)
    List<Product> listProductPriceAsc();

    List<Product> findAll(Specification<Product> spec);
}
