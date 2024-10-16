package com.vnpt.mini_project_java.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@DynamicUpdate
@Table(name = "storage")
@Getter
@Setter
public class Storage {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "import_id")
    private Long idImport;
	
	@Column(columnDefinition = "nvarchar(150)")
	private String users;
	
	
	private int quantity;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "createDate", updatable = false)
	private LocalDate createDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "updateDate", updatable = true)
	private LocalDate updateDate;
	
	@OneToOne
	@JoinColumn(name = "product_id", insertable = true, updatable = true)
	private Product product;
	
	public Storage() {
		
	}
	
	public Storage(Long idImport, String users, int quantity, LocalDate createDate, LocalDate updateDate, Product product) {
		this.idImport = idImport;
		this.users = users;
		this.quantity = quantity;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.product = product;
	}
}
