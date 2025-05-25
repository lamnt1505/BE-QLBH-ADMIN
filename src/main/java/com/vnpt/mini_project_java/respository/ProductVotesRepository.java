package com.vnpt.mini_project_java.respository;

import com.vnpt.mini_project_java.entity.ProductVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVotesRepository extends JpaRepository<ProductVote, Long> {
    List<ProductVote> findByProduct_ProductID(Long productId);
}
