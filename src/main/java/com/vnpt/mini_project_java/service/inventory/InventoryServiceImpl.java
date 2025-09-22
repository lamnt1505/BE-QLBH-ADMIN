package com.vnpt.mini_project_java.service.inventory;

import com.vnpt.mini_project_java.dto.InventoryDTO;
import com.vnpt.mini_project_java.entity.Inventory;
import com.vnpt.mini_project_java.respository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService{

    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Optional<Inventory> checkStock(Long branchID, Long productID) {
        return inventoryRepository.findByBranch_BranchIDAndProduct_ProductID(branchID, productID);
    }

    @Override
    public List<InventoryDTO> getAllBranchesStock(Long productID) {
        return inventoryRepository.findByProduct_ProductID(productID)
                .stream()
                .map(InventoryDTO::new)
                .collect(Collectors.toList());
    }
}
