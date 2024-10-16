package com.vnpt.mini_project_java.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "users")
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    private String accountName;

	private String phone;
	
    private String userPass;

    @Column(columnDefinition = "bit default 1")
    private boolean role;

    @Column(columnDefinition = "nvarchar(50)")
	private String fullname;
    
	private boolean gender;
	
	@Column(name = "date_product")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthday;
	
	@Column(columnDefinition = "nvarchar(150)")
	private String address;
}
