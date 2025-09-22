package com.vnpt.mini_project_java.respository;

import com.vnpt.mini_project_java.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByBranch_BranchIDAndProduct_ProductID(Long branchID, Long productID);

    List<Inventory> findByProduct_ProductID(Long productID);
}
