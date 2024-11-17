package com.vnpt.mini_project_java.restcontroller;


import com.vnpt.mini_project_java.dto.ProductDTO;
import com.vnpt.mini_project_java.entity.Product;
import com.vnpt.mini_project_java.projections.StatisticalForMonthProjections;
import com.vnpt.mini_project_java.projections.StatisticalForYearProjections;
import com.vnpt.mini_project_java.projections.StatisticalProductProjections;
import com.vnpt.mini_project_java.service.product.ProductService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import com.vnpt.mini_project_java.service.statistical.StatisticalService;
import com.vnpt.mini_project_java.service.storage.StorageService;
import com.vnpt.mini_project_java.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/v1/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductRestController {

    private static final Logger logger = LoggerFactory.getLogger(ProductRestController.class);

    @Autowired
    private final ProductService productService;

    @Autowired
    private final StatisticalService statisticsService;

	@Autowired
	StorageService storageService;
	
    public ProductRestController(ProductService productService, StatisticalService statisticsService) {
        this.productService = productService;
        this.statisticsService = statisticsService;
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getListProductdto() {
        return ResponseEntity.ok(productService.getAllProductDTO());
    }

    @GetMapping("/Listgetall")
    public ResponseEntity<List<ProductDTO>> getList() {
        List<ProductDTO> productDTOS = productService.getAllProductDTO();
        return ResponseEntity.ok(productDTOS);
    }

    @GetMapping("/{id}/get")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable(name = "id") Long productID) {
        Product product = productService.getProductById(productID);
        ProductDTO prodouctResponse = new ProductDTO(product);
        return ResponseEntity.ok().body(prodouctResponse);
    }

    @PostMapping("/add")
    public ResponseEntity<ProductDTO> createProduct(ProductDTO dto, MultipartFile image) {
        try {
            ProductDTO createProduct = productService.createProduct(dto, image);
            logger.info("Người dùng đã thêm một sản phẩm mới. ID sản phẩm: {}, Tên sản phẩm: {}", createProduct.getId(), createProduct.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(createProduct);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable long id, ProductDTO productDTO, MultipartFile image){
        try {
            Product product = productService.updateProduct(id,productDTO,image);
            ProductDTO updateDTO = new ProductDTO(product);
            return ResponseEntity.ok(updateDTO);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        try {
            logger.info("Người dùng {} đang xóa sản phẩm có ID: {}",id);
            productService.deleteProductById(id);
            return ResponseEntity.ok().body("{\"status\":\"success\"}");
        } catch (EntityNotFoundException ex) {
            System.out.println("Error" + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/category/{categoryID}/products")
    public ResponseEntity<List<ProductDTO>> getProductsByCategoryId(@PathVariable long categoryID){
        List<ProductDTO> products = productService.getProductsByCategoryId(categoryID);
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            System.out.println(products);
            return ResponseEntity.ok(products);
        }
    }

    @GetMapping("/sales")
    public ResponseEntity<List<StatisticalProductProjections>> getSalesData(){
        List<StatisticalProductProjections> salesData = statisticsService.statisticalForProduct();
        return ResponseEntity.ok(salesData);
    }

    @GetMapping("/yearly")
    public ResponseEntity<List<StatisticalForYearProjections>> getYearlySalesData() {
        List<StatisticalForYearProjections> salesData = statisticsService.statisticalForYear();
        return ResponseEntity.ok(salesData);
    }

    @GetMapping("/month")
    public ResponseEntity<List<StatisticalForMonthProjections>> getMonthdata(){
        List<StatisticalForMonthProjections> Monthdata = statisticsService.statisticalForMonth();
        return ResponseEntity.ok(Monthdata);
    }

    @PostMapping("/import")
    public ResponseEntity<Map<String, String>> importProducts(@RequestParam("file") MultipartFile file) {
        try {
            List<ProductDTO> products = ExcelUtil.readProductsFromExcel(file);
            productService.importProductsFromExcel(products);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Thêm mới thành công");
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Có lỗi khi thêm file");
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadExcelProductTemplate(){
        try{
            InputStream inputStream = getClass().getResourceAsStream("/templates/API-PRODUCT.xlsx");
            InputStreamResource resource = new InputStreamResource(inputStream);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=API-PRODUCT.xlsx");
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/paginated")
    public Page<ProductDTO> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "productID,asc") String[] sort) {

        Sort.Direction sortDirection = Sort.Direction.fromString(sort[1]);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort[0]));

        return productService.getPaginatedProducts(pageable);
    }
}

