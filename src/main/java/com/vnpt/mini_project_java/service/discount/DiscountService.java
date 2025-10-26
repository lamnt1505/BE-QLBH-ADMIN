package com.vnpt.mini_project_java.service.discount;

import com.vnpt.mini_project_java.dto.DiscountDTO;
import com.vnpt.mini_project_java.entity.Discount;

import java.util.Optional;

public interface DiscountService {
    Discount createDiscountCode(DiscountDTO discountDTO);

    Optional<Discount> validateDiscountCode(String discountCode);

    boolean existsByCode(String code);

    double applyDiscount(double totalPrice, String discountCode);

    Optional<Discount> getLatestDiscount();
}
