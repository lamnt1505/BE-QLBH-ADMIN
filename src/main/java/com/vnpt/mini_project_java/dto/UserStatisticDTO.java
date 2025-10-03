package com.vnpt.mini_project_java.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatisticDTO {
    private String month;
    private Long totalUsers;
}
