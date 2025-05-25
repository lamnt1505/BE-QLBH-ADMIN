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
import org.springframework.web.bind.annotation.*;

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

    @GetMapping()
    public String homeuser(ModelMap modelMap, HttpServletRequest request, HttpSession session,
                           @CookieValue(value = "accountName", required = false) String accountName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accountName")) {
                    System.out.println("Cookie accountName: " + cookie.getValue());
                    Optional<Account> optionalAccount = this.accountService.findByname(cookie.getValue());
                    if (optionalAccount.isPresent()) {
                        Account acc = optionalAccount.get();
                        if (session.getAttribute("cart") == null) {
                            session.setAttribute("cart", new ArrayList<>());
                        }
                        modelMap.addAttribute("accountName", acc.getUsername());
                        modelMap.addAttribute("accountID", acc.getAccountID());
                    }
                }
            }
        }
        if (session.getAttribute("cart") == null) {
            session.setAttribute("cart", new ArrayList<>());
        }
        modelMap.addAttribute("product", this.productService.findAll());
        modelMap.addAttribute("category", this.categoryService.findAll());

        return "home/index";
    }

    @GetMapping(value = "/showProductSingle/{productID}")
    public String ShowProductByIdProductDetail(ModelMap model, @PathVariable("productID") long productID) {

        List<ProductVersion> productVersions = this.productVersionService.findAllByProductId(productID);

        model.addAttribute("productversions", productVersions);

        model.addAttribute("productdetail", this.productDetailService.findByIdProduct(productID));

        Optional<Product> productOptional = this.productService.findById(productID);
        if (productOptional.isPresent()) {
            Product showProductSingle = productOptional.get();
            model.addAttribute("showProductSingle", showProductSingle);

            model.addAttribute("categoryName", showProductSingle.getCategory().getCategoryName());
            model.addAttribute("categoryID", showProductSingle.getCategory().getCategoryID());

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
    public String getProduct(HttpServletRequest request, ModelMap model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accountName")) {
                    Optional<Account> optionalAccount = this.accountService.findByname(cookie.getValue());
                    if (optionalAccount.isPresent()) {
                        Account acc = optionalAccount.get();
                        model.addAttribute("accountName", acc.getUsername());
                        model.addAttribute("accountID", acc.getAccountID());
                        return "shop/product";
                    }
                }
            }
        }
        model.addAttribute("product", this.productService.findAll());
        model.addAttribute("category", this.categoryService.findAll());
        return "shop/product";
    }

    @GetMapping("/introduce")
    public String getIntroduce(ModelMap model) {
        model.addAttribute("product", this.productService.findAll());
        model.addAttribute("category", this.categoryService.findAll());
        return "shop/introduce";
    }

    @GetMapping("/searchProduct")
    public String searchProductByIdCategory(ModelMap model, @RequestParam("key") String key) {
        List<Product> products = this.productService.searchListProductByIdCategory(key);

        if (products == null || products.isEmpty()) {
            model.addAttribute("errorMessage", "Không tìm thấy sản phẩm phù hợp với từ khóa: " + key);
            return "shop/searchProduct";
        }

        model.addAttribute("searchProduct", products);
        return "shop/searchProduct";
    }

    @GetMapping("/contact")
    public String getContact(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        model.addAttribute("product", this.productService.findAll());
        model.addAttribute("category", this.categoryService.findAll());
        return "shop/contact";
    }

    @GetMapping("/favorites")
    public String getFavoritePage(ModelMap modelMap, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accountName")) {
                    Optional<Account> optionalAccount = this.accountService.findByname(cookie.getValue());
                    if (optionalAccount.isPresent()) {
                        Account acc = optionalAccount.get();
                        modelMap.addAttribute("accountName", acc.getUsername());
                        modelMap.addAttribute("accountID", acc.getAccountID());
                        return "shop/favorites--list";
                    }
                }
            }
        }
        return "shop/favorites--list";
    }
}
