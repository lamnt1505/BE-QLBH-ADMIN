package com.vnpt.mini_project_java.controller;

import com.vnpt.mini_project_java.entity.Account;
import com.vnpt.mini_project_java.entity.Product;
import com.vnpt.mini_project_java.entity.ProductVersion;
import com.vnpt.mini_project_java.service.account.AccountService;
import com.vnpt.mini_project_java.service.category.CategoryService;
import com.vnpt.mini_project_java.service.product.ProductService;
import com.vnpt.mini_project_java.service.productDetail.ProductDetailService;
import com.vnpt.mini_project_java.service.productVersion.ProductVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping(value = "/index")
public class HomeController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    AccountService accountService;

    @Autowired
    ProductVersionService productVersionService;

    @Autowired
    ProductDetailService productDetailService;

    void getName(HttpServletRequest request, ModelMap modelMap) {
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; ++i) {
            if (cookies[i].getName().equals("accountName")) {
                Optional<Account> accountOptional = this.accountService.findByname(cookies[i].getValue());
                Account acc = accountOptional.orElse(null);
                if (acc != null) {
                    modelMap.addAttribute("accountName", acc.getAccountName());
                    modelMap.addAttribute("accountID", acc.getAccountID());
                }
                break;
            }
        }
    }

    @GetMapping()
    public String homeuser(ModelMap modelMap, HttpServletRequest request,
                           @CookieValue(value ="accountName", required = false) String accountName,
                           HttpServletResponse response, HttpSession session) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; ++i) {
                if (cookies[i].getName().equals("accountName")) {
                    Optional<Account> optionalAccount = this.accountService.findByname(cookies[i].getValue());
                    if (optionalAccount.isPresent()) {
                        Account acc = optionalAccount.get();

                        if (session.getAttribute("cart") == null) {
                            session.setAttribute("cart", new ArrayList<>());
                        }

                        modelMap.addAttribute("accountName", acc.getAccountName());
                        modelMap.addAttribute("accountID", acc.getAccountID());
                    }
                }
            }
        }else {
            if (session.getAttribute("cart") == null) {
                session.setAttribute("cart", new ArrayList<>());
            }
        }

        modelMap.addAttribute("product", this.productService.findAll());
        modelMap.addAttribute("category", this.categoryService.findAll());

        return "home/index";
    }

    @GetMapping(value = "/showProductSingle/{productID}")
    public String ShowProductByIdProductDetail(ModelMap model, @PathVariable("productID") long productID
            ,HttpServletRequest request, HttpServletResponse response) {

        getName(request, model);

        model.addAttribute("product", this.productService.findAll());
        model.addAttribute("category", this.categoryService.findAll());
        List<ProductVersion> productVersions = this.productVersionService.findAllByProductId(productID);
        model.addAttribute("productversions", productVersions);
        model.addAttribute("productdetail", this.productDetailService.findByIdProduct(productID));

        Optional<Product> productOptional = this.productService.findById(productID);
        if (productOptional.isPresent()) {
            Product showProductSingle = productOptional.get();
            model.addAttribute("showProductSingle", showProductSingle);
        } else {
            model.addAttribute("errorMessage", "Không tìm thấy sản phẩm");
            return "home/index";
        }
        Product product = this.productService.findById(productID).get();

        Optional<Product> optionalProduct = this.productService.findById(product.getProductID());
        if (optionalProduct.isPresent()) {
            Product p = optionalProduct.get();
            p.setProductID(product.getProductID());
            p.setProductName(product.getProductName());
            p.setDescription(product.getDescription());
            p.setImage(product.getImage());

            List<Product> list = this.productService.findBycategoryId(p.getCategory().getCategoryID());
            list.removeIf(prod -> prod.getProductID() == product.getProductID());
            model.addAttribute("showProductByCategory", list);
        } else {
            model.addAttribute("errorMessage", "Không tìm thấy sản phẩm");
            return "shop/404";
        }
        return "shop/productSingle";
    }

    @GetMapping("/product")
    public String getProduct(HttpServletRequest request, HttpServletResponse response, ModelMap model){
        getName(request, model);
        model.addAttribute("product", this.productService.findAll());// tìm tất product
        model.addAttribute("category", this.categoryService.findAll());// tên loại để hiển thị 
        return "shop/product";
    }

    @GetMapping("/test")
    public String getTest(HttpServletRequest request, HttpServletResponse response, ModelMap model){
        getName(request, model);
        model.addAttribute("product", this.productService.findAll());
        model.addAttribute("category", this.categoryService.findAll());
        return "shop/test";
    }

}
