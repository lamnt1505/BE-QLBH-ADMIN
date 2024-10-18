package com.vnpt.mini_project_java.restcontroller;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vnpt.mini_project_java.dto.StorageDTO;
import com.vnpt.mini_project_java.entity.Storage;
import com.vnpt.mini_project_java.service.storage.StorageService;

@RestController
@RequestMapping(value = "/api/v1/storage", produces = MediaType.APPLICATION_JSON_VALUE)
public class StorageRestcontroller {

	@Autowired
	private final StorageService storageService;
	
	public StorageRestcontroller(StorageService storageService) {
		this.storageService = storageService;
	}
	
	@GetMapping("/getall")
    public ResponseEntity<?> getListProductdto() {
        return ResponseEntity.ok(storageService.getAllStorageDTO());
    }

    @PostMapping("/add")
	public ResponseEntity<StorageDTO> createStorage(@RequestBody StorageDTO dto) {
		try {
			StorageDTO createdStorage = storageService.createStorage(dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdStorage);
		}catch(EntityNotFoundException ex) {
			return ResponseEntity.badRequest().build();
		}
	}
    
    @PutMapping("/update/{id}")
    public ResponseEntity<StorageDTO> updateStorage(@PathVariable long id, StorageDTO storageDTO){
    	try {
    		Storage storage = storageService.updateStorage(id, storageDTO);
    		
    		StorageDTO updateDTO = new StorageDTO(storage);

    		return ResponseEntity.ok(updateDTO);
    	}catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
            
		}
    }
    
}
