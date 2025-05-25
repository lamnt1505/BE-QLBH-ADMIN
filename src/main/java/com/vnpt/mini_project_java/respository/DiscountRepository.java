package com.vnpt.mini_project_java.respository;

import com.vnpt.mini_project_java.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount,Long> {
    Optional<Discount> findByDiscountCode(String discountCode);
}
