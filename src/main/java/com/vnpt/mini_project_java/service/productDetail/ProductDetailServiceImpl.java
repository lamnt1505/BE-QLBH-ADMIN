package com.vnpt.mini_project_java.service.productDetail;

import com.vnpt.mini_project_java.dto.ProductDetailDTO;
import com.vnpt.mini_project_java.entity.Product;
import com.vnpt.mini_project_java.entity.ProductDetail;
import com.vnpt.mini_project_java.respository.ProductDetailRepository;
import com.vnpt.mini_project_java.respository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

    @Autowired
    private final ProductDetailRepository productDetailRepository;

    @Autowired
    private final ProductRepository productRepository;

    public ProductDetailServiceImpl(ProductDetailRepository productDetailRepository,ProductRepository productRepository) {
        this.productDetailRepository = productDetailRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDetailDTO> getAllProductDetailDTO(){
        List<ProductDetail> productDetails = productDetailRepository.findAll();
        return productDetails.stream().map(ProductDetailDTO::new).collect(Collectors.toList());
    }

    @Override
    public ProductDetail getProductDetailById(long productDetailID){
        Optional<ProductDetail> result = productDetailRepository.findById(productDetailID);
        if(result.isPresent()){
            return result.get();
        }else {
            throw new RuntimeException("ProductDetail not found with ID:" + productDetailID);
        }
    }

    @Override
    public ProductDetailDTO createProductDetail(ProductDetailDTO dto){

        Long productId = dto.getProductID();
        if (productId == null) {
            throw new RuntimeException("");
        }
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new RuntimeException("Product not found with id:" + productId));

        ProductDetail productDetail = new ProductDetail();
        productDetail.setProductDetailID(dto.getProductDetailID());
        productDetail.setProductCamera(dto.getProductCamera());
        productDetail.setProductWifi(dto.getProductWifi());
        productDetail.setProductScreen(dto.getProductScreen());
        productDetail.setProductBluetooth(dto.getProductBluetooth());
        productDetail.setProduct(product);
        ProductDetail savedProductDetail = productDetailRepository.save(productDetail);

        return new ProductDetailDTO(savedProductDetail);
    }

    @Override
    public List<ProductDetail> findByIdProduct(long productID) {
        return productDetailRepository.findByIdProduct(productID);
    }
}
