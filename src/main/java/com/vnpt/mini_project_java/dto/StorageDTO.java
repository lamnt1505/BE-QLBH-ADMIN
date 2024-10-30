package com.vnpt.mini_project_java.dto;

import java.time.format.DateTimeFormatter;
import com.vnpt.mini_project_java.entity.Storage;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class StorageDTO {
	
	private Long idImport;
	
    private String users;
    
    private String createDate;
    
    private String updateDate;
    
    private Long productId;
    
    private String product_name;
    
    private Integer quantity;
    
    public StorageDTO() {
    	
    }
    
    public StorageDTO(Storage storage){
    	this.idImport = storage.getIdImport();
    	this.users = storage.getUsers();
    	this.setQuantity(storage.getQuantity());
    	if (storage.getCreateDate() != null) {
    		this.createDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(storage.getCreateDate());
    	}
        if (storage.getUpdateDate() != null) {
        	this.updateDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(storage.getUpdateDate());
        }
    	if(storage.getProduct() != null){
            this.productId = storage.getProduct().getProductID();
            this.product_name = storage.getProduct().getProductName();
        }
    }
}
