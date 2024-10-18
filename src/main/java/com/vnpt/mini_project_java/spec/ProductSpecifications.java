package com.vnpt.mini_project_java.spec;

import com.vnpt.mini_project_java.criteria.ProductSearchCriteria;
import com.vnpt.mini_project_java.entity.Product;
import com.vnpt.mini_project_java.entity.ProductDetail;
import com.vnpt.mini_project_java.entity.ProductVersion;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecifications {
    public static Specification<Product> searchByCriteria(ProductSearchCriteria criteria){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate predicate;
            
            if (criteria.getProductID() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), criteria.getProductID()));
            }

            if (criteria.getCategoryID() != null) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), criteria.getCategoryID()));
            }
            
            if (criteria.getProductName() != null && !criteria.getProductName().isEmpty()) {
                try {
                    Predicate productNamePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("productName")),
                        "%" + criteria.getProductName().toLowerCase() + "%"
                    );
                    predicates.add(productNamePredicate); 
                } catch (Exception e) {
                    System.out.println();
                }
            }
            if (criteria.getCategoryName() != null && !criteria.getCategoryName().isEmpty()) {
                predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("category").get("name")),
                        "%" + criteria.getCategoryName().toLowerCase() + "%");
                predicates.add(predicate);
            }
            if(criteria.getMemory() != null && !criteria.getMemory().isEmpty()){
                Join<Product,ProductVersion> productVersions = root.join("productVersions");
                predicate = criteriaBuilder.equal(productVersions.get("memory"),criteria.getMemory());
                predicates.add(predicate);
            }
            if (criteria.getColor() != null && !criteria.getColor().isEmpty()){
                Join<Product,ProductVersion> productVersions = root.join("productVersions");
                predicate = criteriaBuilder.equal(productVersions.get("color"),criteria.getColor());
                predicates.add(predicate);
            }
            if(criteria.getPrice() != null && !criteria.getPrice().isEmpty()){
                Join<Product,ProductVersion> productVersions = root.join("productVersions");
                predicate = criteriaBuilder.equal(productVersions.get("price"),criteria.getPrice());
                predicates.add(predicate);
            }
            if(criteria.getProductCamera() != null && !criteria.getProductCamera().isEmpty()){
                Join<Product,ProductDetail> productDetails = root.join("productDetails");
                predicate = criteriaBuilder.equal(productDetails.get("productCamera"),criteria.getProductCamera());
                predicates.add(predicate);
            }
            if (criteria.getProductBluetooth() != null && !criteria.getProductBluetooth().isEmpty()){
                Join<Product,ProductDetail> productDetails = root.join("productDetails");
                predicate = criteriaBuilder.equal(productDetails.get("productBluetooth"),criteria.getProductBluetooth());
                predicates.add(predicate);
            }
            if (criteria.getProductScreen() != null && !criteria.getProductScreen().isEmpty()){
                Join<Product,ProductDetail> productDetails = root.join("productDetails");
                predicate = criteriaBuilder.equal(productDetails.get("productScreen"),criteria.getProductScreen());
                predicates.add(predicate);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        };
    }
}