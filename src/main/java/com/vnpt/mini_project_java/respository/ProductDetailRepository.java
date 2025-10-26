package com.vnpt.mini_project_java.respository;

import com.vnpt.mini_project_java.entity.Product;
import com.vnpt.mini_project_java.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail,Long> {
    @Query(value = "select * from product_detail where product_id", nativeQuery = true)
    List <ProductDetail> findByIdProduct(long productID);

    ProductDetail findByProduct(Product product);
}
