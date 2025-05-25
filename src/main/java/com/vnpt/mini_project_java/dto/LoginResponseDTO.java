package com.vnpt.mini_project_java.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class LoginResponseDTO {

	private String message;
	
	private boolean success;
	
	private String accountName;
	
	private String phoneNumber;
	
	private String phone;
	
	private String redirectPage;


	public LoginResponseDTO() {
	}

}
