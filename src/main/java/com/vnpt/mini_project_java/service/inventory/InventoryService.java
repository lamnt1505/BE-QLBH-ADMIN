package com.vnpt.mini_project_java.service.inventory;

import com.vnpt.mini_project_java.dto.InventoryDTO;
import com.vnpt.mini_project_java.entity.Inventory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface InventoryService {
    Optional<Inventory> checkStock(Long branchID, Long productID);

    List<InventoryDTO> getAllBranchesStock(Long productID);
}
