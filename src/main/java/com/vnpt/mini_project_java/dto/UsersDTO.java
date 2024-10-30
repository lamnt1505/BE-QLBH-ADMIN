package com.vnpt.mini_project_java.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class UsersDTO {

    private Long userID;

    private String userPass;

    private String accountName;

    private String phone;

    private boolean role;

    private String fullname;

    private boolean gender;

    private String birthday;

    private String address;

    private String email;
}
