package com.vnpt.mini_project_java.respository;

import com.vnpt.mini_project_java.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findById(Long cartID);
}
