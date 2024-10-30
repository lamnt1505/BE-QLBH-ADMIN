package com.vnpt.mini_project_java.controller;

import com.vnpt.mini_project_java.entity.Account;
import com.vnpt.mini_project_java.service.account.AccountService;
import com.vnpt.mini_project_java.service.order.OrderService;
import com.vnpt.mini_project_java.service.statistical.StatisticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class StatisticalController {

	@Autowired
	AccountService accountService;
	
    @Autowired
    OrderService orderService;

    @Autowired
    StatisticalService statisticalService;

    void getName(HttpServletRequest request, ModelMap modelMap){
        Cookie[] cookies = request.getCookies();
        for(int i = 0; i < cookies.length; ++i){
            if(cookies[i].getName().equals("accountName")){
                Account users = this.accountService.findByname(cookies[i].getValue()).get();
                modelMap.addAttribute("accountName", users.getAccountName());
                modelMap.addAttribute("accountID",users.getAccountID());
            }
        }
    }

    @GetMapping("/manager/statistical")
    public String manager(ModelMap model, 
                          HttpServletRequest request,
                          @CookieValue(value = "accountName", required = false) String accountName,
                          RedirectAttributes redirect) {
        if (accountName != null) {
            Optional<Account> userOptional = this.accountService.findByname(accountName);
            if (userOptional.isPresent()) {
                Account user = userOptional.get();
                if (user.getIsAdmin()) {
                    model.addAttribute("username", accountName);
                    model.addAttribute("accountName", user.getAccountName());
                    model.addAttribute("years", statisticalService.statisticalForYear());
                    model.addAttribute("products", statisticalService.statisticalForProduct());
                    model.addAttribute("months", statisticalService.statisticalForMonth());
                    return "/manager/statistical/statistical";
                } else {
                    redirect.addFlashAttribute("fail", "Vui lòng sử dụng tài khoản admin!");
                    return "redirect:/manager/listCategory";
                }
            } else {
                redirect.addFlashAttribute("fail", "User not found.");
                return "/manager/statistical/statistical";
            }
        } else {
            return "redirect:/login";
        }
    }

}
