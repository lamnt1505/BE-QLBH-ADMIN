package com.vnpt.mini_project_java.respository;

import com.vnpt.mini_project_java.entity.Product;
import com.vnpt.mini_project_java.entity.ProductDetail;
import com.vnpt.mini_project_java.entity.ProductVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductDetailRepository extends JpaRepository<ProductDetail,Long> {
    @Query(value = "select * from product_detail where product_id", nativeQuery = true)
    List <ProductDetail> findByIdProduct(long productID);
}
