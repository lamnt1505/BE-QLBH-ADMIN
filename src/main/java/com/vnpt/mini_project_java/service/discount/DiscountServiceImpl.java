package com.vnpt.mini_project_java.service.discount;

import com.vnpt.mini_project_java.entity.Discount;
import com.vnpt.mini_project_java.respository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class DiscountServiceImpl implements DiscountService{

    @Autowired
    public final DiscountRepository discountRepository;

    public DiscountServiceImpl(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    
    private double calculateDiscountedAmount(Discount discount, double totalAmount) {
        double discountPercent = discount.getDiscountPercent() / 100.0;
        return totalAmount - (totalAmount * discountPercent);
    }
}
