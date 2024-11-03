package com.vnpt.mini_project_java.restcontroller;

import com.vnpt.mini_project_java.dto.ProductDetailDTO;
import com.vnpt.mini_project_java.entity.ProductDetail;
import com.vnpt.mini_project_java.service.productDetail.ProductDetailService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/productdetail", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductDetailRestController {
    @Autowired
    private ProductDetailService productDetailService;

    @GetMapping("/getall")
    public ResponseEntity<?> getListProductDetail(){
        return ResponseEntity.ok(productDetailService.getAllProductDetailDTO());
    }

    @GetMapping("/Listgetall")
    public ResponseEntity<List<ProductDetailDTO>> getList(){
        List<ProductDetailDTO> productDetailDTOS = productDetailService.getAllProductDetailDTO();
        return ResponseEntity.ok(productDetailDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailDTO> getProductDetailById(@PathVariable(name = "id") Long productDetailID){
        ProductDetail productDetail = productDetailService.getProductDetailById(productDetailID);
        ProductDetailDTO productDetailResponse = new ProductDetailDTO(productDetail);
        return ResponseEntity.ok().body(productDetailResponse);
    }

    @PostMapping("/add")
    public ResponseEntity<ProductDetailDTO> createProductDetail(ProductDetailDTO dto){
        try{
            ProductDetailDTO createProductDetail = productDetailService.createProductDetail(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createProductDetail);
        }catch (EntityNotFoundException ex){
            System.out.println("Error" + ex.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
