package com.vnpt.mini_project_java.controller;


import com.vnpt.mini_project_java.entity.Account;
import com.vnpt.mini_project_java.entity.Product;
import com.vnpt.mini_project_java.service.account.AccountService;
import com.vnpt.mini_project_java.service.category.CategoryService;
import com.vnpt.mini_project_java.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class ViewUserController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    AccountService accountService;

    void getName(HttpServletRequest request, ModelMap modelMap) {
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; ++i) {
            if (cookies[i].getName().equals("accountName")) {
                Optional<Account> accountOptional = this.accountService.findByname(cookies[i].getValue());
                Account acc = accountOptional.orElse(null);
                if (acc != null) {
                    modelMap.addAttribute("accountName", acc.getUsername());
                    modelMap.addAttribute("accountID", acc.getAccountID());
                }
                break;
            }
        }
    }

    @GetMapping("/catalog-list/{categoryID}")
    public String getProductsByCategoryId(@PathVariable("categoryID") long categoryID, ModelMap model,
                                          HttpServletRequest request) {
        model.addAttribute("product", this.productService.findAll());
        model.addAttribute("category", this.categoryService.findAll());

        Optional<Product> p = this.productService.findById(categoryID);
        if (p == null) {
            return "shop/catalog-list";
        }
        getName(request, model);
        model.addAttribute("showProductByIdCategory",
                this.productService.showListCategoryByIdCategory(categoryID));
        return "shop/catalog-list";
    }

    @GetMapping("/login")
    public String login() {
        loginFailed = false;
        return "login1/login";
    }

    @GetMapping("/forgot")
    public String forgot() {
        loginFailed = false;
        return "login1/forgot--password";
    }
	
    @GetMapping("/registerd") public String registerd(Model model) {
		 model.addAttribute("account", new Account()); 
		 return "login1/registerd"; 
    }

    @GetMapping("/updateProfile/{accountID}")
    public String updateCus(ModelMap model, @PathVariable(name = "accountID") long accountID, HttpServletRequest request) {
        model.addAttribute("account", this.accountService.findById(accountID).orElse(null));
        getName(request, model);
        return "login1/updateProfile";
    }
    boolean loginFailed = false;
}