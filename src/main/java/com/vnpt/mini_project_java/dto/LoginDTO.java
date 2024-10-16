package com.vnpt.mini_project_java.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Setter
@Getter
public class LoginDTO {
	
	   	private String  accountName;

	    private String  accountPass;

	    private boolean isAdmin;

		private String email;

		private String phoneNumber;

		private String phone;
		
		private String  userPass;
		
		private LocalDate dateOfBirth;

		public LoginDTO(String accountName, String phoneNumber, String accountPass, boolean isAdmin,String phone,
						String email, LocalDate dateOfBirth, String userPass) {
			this.accountName = accountName;
			this.accountPass = accountPass;
			this.phone = phone;
			this.userPass = userPass;
			this.phoneNumber = phoneNumber;
			this.dateOfBirth = dateOfBirth;
			this.email = email;
			this.isAdmin = isAdmin;
		}

		public LoginDTO() {
		}
}
