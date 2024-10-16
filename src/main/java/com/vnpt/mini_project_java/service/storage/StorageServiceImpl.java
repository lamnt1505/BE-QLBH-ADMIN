package com.vnpt.mini_project_java.service.storage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vnpt.mini_project_java.dto.StorageDTO;
import com.vnpt.mini_project_java.entity.Category;
import com.vnpt.mini_project_java.entity.Product;
import com.vnpt.mini_project_java.entity.Storage;
import com.vnpt.mini_project_java.respository.CategoryRepository;
import com.vnpt.mini_project_java.respository.ProductRepository;
import com.vnpt.mini_project_java.respository.StorageRepository;



@Service
public class StorageServiceImpl implements StorageService{
	
	@Autowired
	 private final StorageRepository storageRepository;
	 
	 @Autowired
	 private final ProductRepository productRepository;
	 
	 @Autowired
	 private final CategoryRepository categoryRepository;
	 
	 private static final String DATE_FORMAT = "yyyy-MM-dd";
	 
	 private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
	 
	 public StorageServiceImpl(StorageRepository storageRepository, ProductRepository productRepository,CategoryRepository categoryRepository) {
    	super();
        this.storageRepository = storageRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }
	 
	 @Override 
	 public List<StorageDTO> getAllStorageDTO() { 
		 List<Storage> products = storageRepository.findAll(); 
		 return products.stream().map(StorageDTO::new).collect(Collectors.toList()); 
	 }
	 
	 @Override
	 public StorageDTO createStorage(StorageDTO storageDTO) {
		 
		 //Storage storage = new Storage();
		 
		 Storage storage = new Storage();
		 
		 storage.setIdImport(storageDTO.getIdImport());
		 
		 storage.setUsers(storageDTO.getUsers());
		 
		 storage.setQuantity(storageDTO.getQuantity());
		 
		 storage.setCreateDate(LocalDate.parse(storageDTO.getCreateDate(),dateTimeFormatter));
		 
		 storage.setUpdateDate(LocalDate.parse(storageDTO.getUpdateDate(),dateTimeFormatter));
		 
		 if (storageDTO.getProductId() == null) {
		        throw new RuntimeException("Product ID cannot be null");
		 }
		 
		 Product product = productRepository.findById(storageDTO.getProductId())
                 .orElseThrow(() -> new RuntimeException("Product not found"));
		 
		 storage.setProduct(product);
		 
		 Storage savedStorage = storageRepository.save(storage);
		
		 return new StorageDTO(savedStorage);
	 }
	 
	 @Override
	 public Storage updateStorage(long idImport, StorageDTO storageDTO) {
		 
		 Optional<Storage> optionalStorage = storageRepository.findById(idImport);
		 if (!optionalStorage.isPresent()) {
	            throw new RuntimeException("Product with ID " + idImport + " not found");
	      }
		 
		 Storage storage = optionalStorage.get();
		 
		 storage.setUsers(storageDTO.getUsers());
		 
		 storage.setQuantity(storageDTO.getQuantity());
		 
		 if (storageDTO.getCreateDate() != null) {
			 storage.setCreateDate(LocalDate.parse(storageDTO.getCreateDate(), dateTimeFormatter));
		 }
		 
		 if (storageDTO.getCreateDate() != null) {
			 storage.setUpdateDate(LocalDate.parse(storageDTO.getUpdateDate(), dateTimeFormatter));
		 }
		 
		 if(storageDTO.getProductId() != null) {
			 Long productId = storageDTO.getProductId();
			 Product product = productRepository.findById(productId)
	                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + productId));
			 storage.setProduct(product);
		 }
		 
		return storageRepository.save(storage);
	 } 
}
