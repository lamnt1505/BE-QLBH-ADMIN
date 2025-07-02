package com.vnpt.mini_project_java.controller;

import com.vnpt.mini_project_java.config.VnpayConfig;

import com.vnpt.mini_project_java.entity.Order;
import com.vnpt.mini_project_java.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;


@Controller
public class VnpayReturnController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/vnpay-return")
    public String vnpayReturn(HttpServletRequest request, Model model) {
        Map<String, String> fields = new HashMap<>();

        Map<String, String[]> params = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            String key = entry.getKey();
            if (!key.equals("vnp_SecureHash") && !key.equals("vnp_SecureHashType")) {
                fields.put(key, entry.getValue()[0]);
            }
        }

        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        for (int i = 0; i < fieldNames.size(); i++) {
            String name = fieldNames.get(i);
            String value = fields.get(name);
            try {
                hashData.append(name).append('=').append(URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Lỗi encoding UTF-8", e);
            }

            if (i < fieldNames.size() - 1) {
                hashData.append('&');
            }
        }

        String myHash = VnpayConfig.hmacSHA512(VnpayConfig.vnp_HashSecret, hashData.toString());
        String vnpSecureHash = request.getParameter("vnp_SecureHash");

        System.out.println("==== DỮ LIỆU NHẬN TỪ VNPAY ====");
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            System.out.println(entry.getKey() + " = " + Arrays.toString(entry.getValue()));
        }
        System.out.println("Chuỗi hashData gửi đi: " + hashData);
        System.out.println("SecureHash từ VNPAY:   " + vnpSecureHash);
        System.out.println("SecureHash mình tính: " + myHash);
        System.out.println("=================================");

        if (myHash.equalsIgnoreCase(vnpSecureHash)) {
            String responseCode = request.getParameter("vnp_ResponseCode");
            String txnRef = request.getParameter("vnp_TxnRef");

            Optional<Order> optionalOrder = orderService.findByOrderCode(txnRef);
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                if ("00".equals(responseCode)) {
                    order.setStatus("Đã thanh toán");
                    model.addAttribute("message", "Thanh toán thành công!");
                } else {
                    order.setStatus("Thanh toán lỗi: " + responseCode);
                    model.addAttribute("message", "Thanh toán thất bại! Mã lỗi: " + responseCode);
                }
                orderService.save(order);
            } else {
                model.addAttribute("message", "Không tìm thấy đơn hàng tương ứng mã: " + txnRef);
            }
        } else {
            model.addAttribute("message", "Chữ ký không hợp lệ - dữ liệu không đáng tin.");
        }

        return "shop/vnpay-result";
    }
}
