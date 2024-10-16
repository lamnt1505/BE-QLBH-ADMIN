package com.vnpt.mini_project_java.service.storage;

import java.util.List;

import com.vnpt.mini_project_java.dto.StorageDTO;
import com.vnpt.mini_project_java.entity.Storage;

public interface StorageService {

	List<StorageDTO> getAllStorageDTO();

	StorageDTO createStorage(StorageDTO dto);

	Storage updateStorage(long idImport, StorageDTO storageDTO);

	//Storage updateStorage(long idImport, StorageDTO storageDTO);
}
