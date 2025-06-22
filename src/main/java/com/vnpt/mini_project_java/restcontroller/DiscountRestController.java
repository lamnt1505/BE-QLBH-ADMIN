package com.vnpt.mini_project_java.restcontroller;


import com.vnpt.mini_project_java.dto.DiscountDTO;
import com.vnpt.mini_project_java.entity.Discount;
import com.vnpt.mini_project_java.service.discount.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping(value = "/api/v1/discounts", produces = MediaType.APPLICATION_JSON_VALUE)
@Component
public class DiscountRestController {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private HttpSession session;

    @PostMapping("/generate")
    public ResponseEntity<Map<String, Object>> generateDiscountCode(@RequestBody DiscountDTO discountDTO, HttpSession session) {
        Discount discount = discountService.createDiscountCode(discountDTO);

        session.setAttribute("generatedDiscountCode", discount.getDiscountCode());

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("discountCode", discount.getDiscountCode());
        response.put("message", "Tạo mã giảm giá thành công!");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getDiscountCode")
    public ResponseEntity<Map<String, Object>> getDiscountCode(HttpSession session) {
        String discountCode = (String) session.getAttribute("generatedDiscountCode");
        Map<String, Object> response = new HashMap<>();
        if (discountCode != null) {
            response.put("success", true);
            response.put("discountCode", discountCode);
        } else {
            response.put("success", false);
            response.put("message", "Không có mã giảm giá nào trong phiên hiện tại.");
        }
        return ResponseEntity.ok(response);
    }
}
