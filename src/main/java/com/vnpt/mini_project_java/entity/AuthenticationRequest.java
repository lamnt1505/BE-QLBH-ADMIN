package com.vnpt.mini_project_java.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class AuthenticationRequest {

    private String accountName;

    private String accountPass;

    public AuthenticationRequest() {
    }

}
