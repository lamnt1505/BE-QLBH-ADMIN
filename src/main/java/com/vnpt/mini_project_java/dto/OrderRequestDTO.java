package com.vnpt.mini_project_java.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderRequestDTO {
    private String receiverName;

    private String receiverPhone;

    private String shippingAddress;

    private String note;
}
