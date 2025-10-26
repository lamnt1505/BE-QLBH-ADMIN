package com.vnpt.mini_project_java.service.discount;


import com.vnpt.mini_project_java.dto.DiscountDTO;
import com.vnpt.mini_project_java.entity.Discount;
import com.vnpt.mini_project_java.respository.DiscountRepository;
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
        if (discountRepository.existsByDiscountCodeIgnoreCase(discountDTO.getDiscountName())) {
            throw new IllegalArgumentException("⚠ Mã giảm giá đã tồn tại!");
        }

        String code = generateReadableCode(discountDTO.getDiscountName());

        Discount discount = new Discount();
        discount.setDiscountCode(code);
        discount.setDiscountName(discountDTO.getDiscountName());
        discount.setDiscountPercent(discountDTO.getDiscountPercent());
        discount.setDateStart(LocalDate.parse(discountDTO.getDateStart(),dateTimeFormatter));
        discount.setDateFinish(LocalDate.parse(discountDTO.getDateFinish(),dateTimeFormatter));

        return discountRepository.save(discount);
    }

    private String generateReadableCode(String name) {
        String prefix = (name != null && !name.isEmpty())
                ? name.toUpperCase().replaceAll("\\s+", "")
                : "SALE";
        String year = String.valueOf(LocalDate.now().getYear());
        int random = (int) (Math.random() * 90 + 10);
        return prefix + year + random;
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
    public boolean existsByCode(String code) {
        return discountRepository.existsByDiscountCodeIgnoreCase(code);
    }

    @Override
    public double applyDiscount(double price, String discountCode) {
        Discount discount = discountRepository.findByDiscountCode(discountCode)
                .orElseThrow(() -> new IllegalArgumentException("Mã giảm giá không tồn tại: " + discountCode));

        LocalDate today = LocalDate.now();

        if (discount.getDateStart() != null && today.isBefore(discount.getDateStart())) {
            throw new IllegalArgumentException("⚠ Mã giảm giá chưa được áp dụng.");
        }

        if (discount.getDateFinish() != null && today.isAfter(discount.getDateFinish())) {
            throw new IllegalArgumentException("⚠ Mã giảm giá đã hết hạn.");
        }

        double discountAmount = (discount.getDiscountPercent());
        return price * (1 - discountAmount / 100);
    }

    @Override
    public Optional<Discount> getLatestDiscount() {
        return discountRepository.findTopByOrderByDateStartDesc();
    }
}
