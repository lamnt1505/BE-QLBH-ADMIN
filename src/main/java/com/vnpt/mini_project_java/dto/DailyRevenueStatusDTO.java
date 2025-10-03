package com.vnpt.mini_project_java.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DailyRevenueStatusDTO {

    private String day;

    private String status;

    private Double revenue;
}
