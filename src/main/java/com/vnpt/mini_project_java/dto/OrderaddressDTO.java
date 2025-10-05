package com.vnpt.mini_project_java.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
public class OrderaddressDTO {

    private Long orderID;

    private Long accountID;

    private LocalDate orderDateImport;

    private String status;

    private String receiverName;

    private String receiverPhone;

    private String shippingAddress;

    private String note;

    private double orderTotal;

    private String username;

    private String email;

    private String phoneNumber;

    private String local;

    public OrderaddressDTO(Long orderID, Long accountID, LocalDate orderDateImport,
                           String status, String receiverName, String receiverPhone,
                           String shippingAddress, String note, double orderTotal,
                           String username, String phoneNumber, String local, String email) {
        this.orderID = orderID;
        this.accountID = accountID;
        this.orderDateImport = orderDateImport;
        this.status = status;
        this.receiverName = (receiverName != null && !receiverName.isEmpty()) ? receiverName : username;
        this.receiverPhone = (receiverPhone != null && !receiverPhone.isEmpty()) ? receiverPhone : phoneNumber;
        this.shippingAddress = (shippingAddress != null && !shippingAddress.isEmpty()) ? shippingAddress : local;
        this.note = note;
        this.orderTotal = orderTotal;
        this.email = email;
    }
}
