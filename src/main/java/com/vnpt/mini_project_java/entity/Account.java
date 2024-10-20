package com.vnpt.mini_project_java.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Account")
@Getter
@Setter
@Data
@AllArgsConstructor
public class Account {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long   accountID;

    @Column(name = "account_name")
    private String  accountName;

    @Column(name = "image")
    private String image;

    @Column(name = "account_pass")
    private String  accountPass;
    
    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "date_of_birth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    
    @Column(name = "is_admin", columnDefinition = "bit default 1")
    private Boolean  isAdmin;
    
    public Account(Long accountID, String accountName, String accountPhone, String accountPass, boolean isAdmin) {
        this.accountID = accountID;
        this.accountName = accountName;
        this.accountPass = accountPass;
        this.isAdmin = isAdmin;
    }

    public Account() {
	}

	@JsonManagedReference
    @JsonBackReference
    @OneToMany(mappedBy ="account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Cart> carts = new HashSet<>();

    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy ="account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ProductVote> productVotes = new HashSet<>();

    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy ="account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Order> orders = new HashSet<>();

	public boolean hasPermission(String string) {
		return false;
	}
}
