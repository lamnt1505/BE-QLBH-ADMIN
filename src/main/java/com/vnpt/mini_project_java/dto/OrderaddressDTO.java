package com.vnpt.mini_project_java.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderaddressDTO {

    private Long orderID;

    private LocalDate orderDateImport;

    private String status;

    private String receiverName;

    private String receiverPhone;

    private String shippingAddress;

    private String note;

    private double orderTotal;

    private String username;

    private String email;
}
