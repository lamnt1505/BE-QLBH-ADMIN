package com.vnpt.mini_project_java.service.productVersion;

import com.vnpt.mini_project_java.dto.ProductVersionDTO;
import com.vnpt.mini_project_java.entity.ProductVersion;
import com.vnpt.mini_project_java.respository.ProductVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductVersionServiceImpl implements ProductVersionService{
    @Autowired
    private  ProductVersionRepository productVersionRepository;

    public ProductVersionServiceImpl(ProductVersionRepository productVersionRepository) {
        this.productVersionRepository = productVersionRepository;
    }

    @Override
    public List<ProductVersionDTO> getAllProductVersionDTO(){
        List<ProductVersion> productVersions = productVersionRepository.findAll();
        return productVersions.stream().map(ProductVersionDTO::new).collect(Collectors.toList());
    }

    @Override
    public ProductVersion getProductVersionById(long versionID){
        Optional<ProductVersion> result = productVersionRepository.findById(versionID);
        if(result.isPresent()){
            return result.get();
        }else {
            throw new RuntimeException("ProductVersion not found with ID:" + versionID);
        }
    }

    @Override
    public List<ProductVersion> findAll(){
        return productVersionRepository.findAll();
    }

    @Override
    public List<ProductVersion> findAllByProductId(long productID){
        return productVersionRepository.findAllByProductId(productID);
    }

}
