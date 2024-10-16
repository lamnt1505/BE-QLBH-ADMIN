package com.vnpt.mini_project_java.respository;

import com.vnpt.mini_project_java.entity.Cart;
import com.vnpt.mini_project_java.entity.CartDetail;
import com.vnpt.mini_project_java.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long>{
    CartDetail findByCartAndProduct(Cart cart, Product product);
}
