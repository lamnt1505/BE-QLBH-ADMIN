package com.vnpt.mini_project_java.controller;


import com.vnpt.mini_project_java.entity.Account;
import com.vnpt.mini_project_java.entity.Product;
import com.vnpt.mini_project_java.entity.Users;
import com.vnpt.mini_project_java.service.account.AccountService;
import com.vnpt.mini_project_java.service.category.CategoryService;
import com.vnpt.mini_project_java.service.product.ProductService;
import com.vnpt.mini_project_java.service.productDetail.ProductDetailService;
import com.vnpt.mini_project_java.service.productVersion.ProductVersionService;
import com.vnpt.mini_project_java.service.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class ViewUserController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    @Autowired
    ProductVersionService productVersionService;

    @Autowired
    ProductDetailService productDetailService;

    void getName(HttpServletRequest request, ModelMap modelMap) {
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; ++i) {
            if (cookies[i].getName().equals("accountName")) {
                Optional<Account> accountOptional = this.accountService.findByname(cookies[i].getValue());
                Account acc = accountOptional.orElse(null); // hoặc accountOptional.orElseGet(() -> null)
                if (acc != null) {
                    modelMap.addAttribute("accountName", acc.getAccountName());
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
    public String login(Model model) {
        model.addAttribute("category", this.categoryService.findAll());
        loginFailed = false;
        return "login1/login";
    }

    boolean loginFailed = false;
    @PostMapping("/login")
    public String login(@RequestParam("accountName") String accountName,
                        @RequestParam("accountName") String loginInput,
                        @RequestParam("accountPass") String accountPass,
                        @RequestParam(value = "rememberMe", defaultValue = "false") boolean rememberMe,
                        HttpServletResponse response,
                        ModelMap model) {
        Optional<Users> optionalUsers = userService.findByname(accountName);
        if (optionalUsers.isPresent()) {
            Users users = optionalUsers.get();
            if (users.getUserPass().equals(accountPass)) {
                Cookie cookie = new Cookie("accountName", users.getAccountName());
                cookie.setMaxAge(7 * 24 * 60 * 60);
                response.addCookie(cookie);
                return "redirect:/manager/index";
            } else {
                model.addAttribute("errorpass", "Mật Khẩu Không Chính Xác");
                return "login1/login";
            }
        } else {
            Optional<Account> optionalByName = accountService.findByname(loginInput);
            Optional<Account> optionalByPhone = accountService.findByphone(loginInput);
            if (optionalByName.isPresent() || optionalByPhone.isPresent()) {
                if (optionalByName.isPresent() && optionalByName.get().getAccountPass().equals(accountPass)) {
                    Cookie cookie = new Cookie("accountName", optionalByName.get().getAccountName());
                    cookie.setMaxAge(7 * 24 * 60 * 60);
                    response.addCookie(cookie);
                    return "redirect:/index";
                } else if (optionalByPhone.isPresent() && optionalByPhone.get().getAccountPass().equals(accountPass)) {
                    Cookie cookie = new Cookie("accountName", optionalByPhone.get().getAccountName());
                    cookie.setMaxAge(7 * 24 * 60 * 60);
                    response.addCookie(cookie);
                    return "redirect:/index";
                } else {
                    loginFailed = true;
                    model.addAttribute("loginInput", loginInput);
                    model.addAttribute("error", "Mật khẩu không chính xác");
                    return "login1/login";
                }
            } else {
                loginFailed = true;
                model.addAttribute("loginInput", loginInput);
                model.addAttribute("error", "Tài khoản hoặc số điện thoại không tồn tại");
                if (rememberMe) {
                    // Nếu người dùng chọn Remember Me, thì tạo cookie với thời gian sống lâu hơn
                    Cookie cookie = new Cookie("accountName", accountName);
                    cookie.setMaxAge(30 * 24 * 60 * 60); // Ví dụ: 30 ngày
                    response.addCookie(cookie);
                }
                return "login1/login";
            }
        }
    }

	
	  @GetMapping("/registerd") public String registerd(Model model) {
		 model.addAttribute("category", this.categoryService.findAll());
		 model.addAttribute("account", new Account()); 
		 return "login1/registerd"; 
	  }


    @GetMapping("/updateProfile/{accountID}")
    public String updateCus(ModelMap model, @PathVariable(name = "accountID") long accountID, HttpServletRequest request) {
        model.addAttribute("listuser", this.accountService.findAll());
        model.addAttribute("account", this.accountService.findById(accountID).orElse(null));
        getName(request, model);
        return "login1/updateProfile";
    }

    @PostMapping("/updateProfile")
    public String updateCus(@ModelAttribute("accountID") @Valid Account account,
                            @CookieValue(value = "accountName", required = false) String accountName,
                            HttpServletRequest request, ModelMap model) {
        //account.setAccountPass(passwordEncoder.encode(account.getAccountPass()));
        //accountService.save(account);
        return "redirect:/index";
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                cookie.setValue("");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        return "redirect:/index";
    }

}