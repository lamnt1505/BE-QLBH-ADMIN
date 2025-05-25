package com.vnpt.mini_project_java.controller;

import com.vnpt.mini_project_java.entity.Account;
import com.vnpt.mini_project_java.entity.OrderDetail;
import com.vnpt.mini_project_java.entity.Product;
import com.vnpt.mini_project_java.service.account.AccountService;
import com.vnpt.mini_project_java.service.cart.CartService;
import com.vnpt.mini_project_java.service.category.CategoryService;
import com.vnpt.mini_project_java.service.order.OrderService;
import com.vnpt.mini_project_java.service.orderDetail.OrderDetailService;
import com.vnpt.mini_project_java.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    ProductService productService;

    @Autowired
    AccountService accountService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderDetailService orderDetailService;

    @GetMapping("/cart")
    public String viewCart(ModelMap model, HttpServletRequest request, HttpSession session) {
        model.addAttribute("product", this.productService.findAll());
        model.addAttribute("category", this.categoryService.findAll());
        long id = -1;
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("accountName")) {
                Optional<Account> accountOptional = this.accountService.findByname(cookies[i].getValue());
                if (accountOptional.isPresent()) {
                    Account account = accountOptional.get();
                    model.addAttribute("accountName", account.getUsername());
                    id = account.getAccountID();
                }
                break;
            }
        }
        if(session.getAttribute("cart") == null){
            session.setAttribute("cart", new ArrayList<>());
        }
        if (this.orderService.listInvoiceByAccount(id).size() == 0) {
            model.addAttribute("orders", new ArrayList<>());
        } else {
            model.addAttribute("orders", this.orderService.listInvoiceByAccount(id));
        }
        return "shop/cart";
    }

    @GetMapping("/orderdetails/{orderDetailID}")
    public String viewOrderdetails(@PathVariable("orderDetailID") long orderDetailID, ModelMap model, HttpServletRequest request){
        long id = -1;
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("accountName")) {
                Account account = this.accountService.findByname(cookies[i].getValue()).get();
                model.addAttribute("accountName", account.getUsername());
                if (account != null) {
                    id = account.getAccountID();
                }
                break;
            }
        }
        List<OrderDetail> list = this.orderDetailService.findDetailByInvoiceId(orderDetailID);
        List<Product> productorder = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            Product odrProduct = productService.findByIdProduct(list.get(i).getProduct().getProductID());
            odrProduct.setAmount(list.get(i).getAmount());
            productorder.add(odrProduct);
        }
        model.addAttribute("oldorders",productorder);
        return "shop/orderdetail";
    }
}
