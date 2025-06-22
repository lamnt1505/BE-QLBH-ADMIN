package com.vnpt.mini_project_java.service.discount;


import com.vnpt.mini_project_java.dto.DiscountDTO;
import com.vnpt.mini_project_java.entity.Discount;
import com.vnpt.mini_project_java.respository.DiscountRepository;
import com.vnpt.mini_project_java.util.DiscountCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class DiscountServiceImpl implements DiscountService {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    @Autowired
    DiscountRepository discountRepository;

    @Override
    public Discount createDiscountCode(DiscountDTO discountDTO) {
        String code = DiscountCodeGenerator.generateBase64DiscountCode(20);
        Discount discount = new Discount();
        discount.setDiscountCode(code);
        discount.setDiscountName(discountDTO.getDiscountName());
        discount.setDiscountPercent(discountDTO.getDiscountPercent());
        discount.setDateStart(LocalDate.parse(discountDTO.getDateStart(),dateTimeFormatter));
        discount.setDateFinish(LocalDate.parse(discountDTO.getDateFinish(),dateTimeFormatter));

        return discountRepository.save(discount);
    }

    @Override
    public Optional<Discount> validateDiscountCode(String discountCode) {
        Optional<Discount> discount = discountRepository.findByDiscountCode(discountCode);
        if (discount.isPresent()) {
            LocalDate today = LocalDate.now();
            if (!discount.get().getDateFinish().isBefore(today)) {
                return discount;
            }
        }
        return Optional.empty();
    }

    @Override
    public double applyDiscount(double price, String discountCode) {
        Discount discount = discountRepository.findByDiscountCode(discountCode)
                .orElseThrow(() -> new IllegalArgumentException("Discount code invalid"));
        LocalDate today = LocalDate.now();

        // Kiểm tra ngày bắt đầu
        if (discount.getDateStart() != null && today.isBefore(discount.getDateStart())) {
            throw new IllegalArgumentException("Mã giảm giá chưa được áp dụng.");
        }

        // Kiểm tra ngày kết thúc
        if (discount.getDateFinish() != null && today.isAfter(discount.getDateFinish())) {
            throw new IllegalArgumentException("Mã giảm giá đã hết hạn.");
        }
        double discountAmount = (discount.getDiscountPercent() / 100.0) * price;
        return price - discountAmount;
    }
}
