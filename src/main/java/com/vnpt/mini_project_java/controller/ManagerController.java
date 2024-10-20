package com.vnpt.mini_project_java.controller;

import com.vnpt.mini_project_java.dto.CategoryDTO;
import com.vnpt.mini_project_java.dto.ProductDTO;
import com.vnpt.mini_project_java.dto.StorageDTO;
import com.vnpt.mini_project_java.dto.TrademarkDTO;
import com.vnpt.mini_project_java.entity.*;
import com.vnpt.mini_project_java.restcontroller.AccountRestController;
import com.vnpt.mini_project_java.service.account.AccountService;
import com.vnpt.mini_project_java.service.category.CategoryService;
import com.vnpt.mini_project_java.service.order.OrderService;
import com.vnpt.mini_project_java.service.orderDetail.OrderDetailService;
import com.vnpt.mini_project_java.service.product.ProductService;
import com.vnpt.mini_project_java.service.storage.StorageService;
import com.vnpt.mini_project_java.service.users.UserService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/manager")
public class ManagerController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private UserService userService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private StorageService storageService;

	@Autowired
	private OrderService orderService;
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AccountRestController.class);
	
	void getName(HttpServletRequest request, ModelMap modelMap) {
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; ++i) {
			if (cookies[i].getName().equals("accountName")) {
				Users users = this.userService.findByname(cookies[i].getValue()).get();
				modelMap.addAttribute("accountName", users.getAccountName());
				modelMap.addAttribute("accountID", users.getUserID());
			}
		}
	}
	
	@GetMapping()
	public String manager(ModelMap model, @CookieValue(value = "accountName", required = true) String accountName,
			HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accountName")) {
                    Optional<Account> userOptional = accountService.findByname(cookie.getValue());
                    if (userOptional.isPresent()) {
                        Account account = userOptional.get();
                        model.addAttribute("accountName", accountName);
                        model.addAttribute("fullname", account.getAccountName());
                        if (account.getIsAdmin()) {
                        	return "manager/category/listCategory";
                        } else {
                        	return "manager/category/listCategory";
                        }
                    } else {
                        return "redirect:/login";
                    }
                }
            }
        }
        return "redirect:/login";
	}

	@GetMapping("/index")
	public String getIndex(ModelMap model, @CookieValue(value = "accountName", required = true) String username) {
	    Optional<Account> userOptional = accountService.findByname(username);
	    if (userOptional.isPresent()) {
	        Account user = userOptional.get();
	        List<CategoryDTO> categoryList = categoryService.getAllCategoryDTO();
	        model.addAttribute("categoryList", categoryList);
	        model.addAttribute("accountName", user.getAccountName());
	        model.addAttribute("accountID", user.getAccountID());
	        return "manager/home/index";
	    } else {
	        return "redirect:/login";
	    }
	}


	@GetMapping("/listProduct")
	public String getProduct(ModelMap model, HttpServletRequest request) {
	    List<ProductDTO> productList = productService.getAllProductDTO();
	    request.getSession().setAttribute("product", null);
	    String accountName = getAccountNameFromCookies(request);
	    if (accountName != null) {
	        Optional<Account> userOptional = accountService.findByname(accountName);
	        if (userOptional.isPresent()) {
	            Account account = userOptional.get();
	            model.addAttribute("accountName", accountName);
	            model.addAttribute("fullname", account.getAccountName());
	        } else {
	            return "redirect:/login";
	        }
	    } else {
	        return "redirect:/login";
	    }
	    model.addAttribute("productList", productList);
	    return "manager/product/listProduct";
	}


	private String getAccountNameFromCookies(HttpServletRequest request) {
	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if (cookie.getName().equals("accountName")) {
	                return cookie.getValue();
	            }
	        }
	    }
	    return null;
	}

	@GetMapping("/addProduct")
	public String addProduct(ModelMap model, HttpServletRequest request) {
	    String accountName = getAccountNameFromCookies(request);
	    if (accountName != null) {
	        Optional<Users> userOptional = this.userService.findByname(accountName);
	        if (userOptional.isPresent()) {
	            Users user = userOptional.get();
	            getName(request, model);
	            model.addAttribute("product", new ProductDTO());
	            return "manager/product/addProduct";
	        } else {
	        	return "manager/product/addProduct";
	        }
	    } else {
	        return "redirect:/login";
	    }
	}

	@GetMapping("/listCategory")
	public String getCategory(ModelMap model, HttpServletRequest request) {
	    String accountName = getAccountNameFromCookies(request);
	    if (accountName != null) {
	        Optional<Users> userOptional = this.userService.findByname(accountName);
	        if (userOptional.isPresent()) {
	            List<CategoryDTO> categoryList = categoryService.getAllCategoryDTO();
	            getName(request, model);
	            model.addAttribute("categoryList", categoryList);
	            return "manager/category/listCategory";
	        } else {
	        	return "manager/category/listCategory";
	        }
	    } else {
	        return "redirect:/login";
	    }
	}

	@GetMapping("/addCategory")
	public String addCategory(ModelMap model, HttpServletRequest request) {
	    String accountName = getAccountNameFromCookies(request);
	    if (accountName != null) {
	        Optional<Users> userOptional = this.userService.findByname(accountName);
	        if (userOptional.isPresent()) {
	            getName(request, model);
	            model.addAttribute("category", new CategoryDTO());
	            return "manager/category/addCategory";
	        } else {
	        	return "manager/category/addCategory";
	        }
	    } else {
	        return "redirect:/login";
	    }
	}
	@GetMapping("/listStorage")
	public String getStorage(ModelMap model,
							 @CookieValue(value = "accountName", required = false) String accountName,
							 HttpServletRequest request) {
		if (accountName != null) {
			Optional<Users> userOptional = this.userService.findByname(accountName);
			if (userOptional.isPresent()) {
				List<StorageDTO> storageList = storageService.getAllStorageDTO();
				getName(request, model);
				model.addAttribute("storageList", storageList);
				return "manager/storage/listStorage";
			} else {
				return "manager/storage/listStorage";
			}
		} else {
			return "redirect:/login";
		}
	}
	@GetMapping("/listTrademark")
	public String getTrademark(ModelMap model, 
	                           @CookieValue(value = "accountName", required = false) String accountName,
	                           HttpServletRequest request) {
	    if (accountName != null) {
	        Optional<Users> userOptional = this.userService.findByname(accountName);
	        if (userOptional.isPresent()) {
	            Users user = userOptional.get();
	            List<CategoryDTO> categoryList = categoryService.getAllCategoryDTO();
	            getName(request, model);
	            model.addAttribute("categoryList", categoryList);
	            return "manager/trademark/listTrademark";
	        } else {
	        	return "manager/trademark/listTrademark";
	        }
	    } else {
	        return "redirect:/login";
	    }
	}

	@GetMapping("/addTrademark")
	public String addTrademark(ModelMap model, HttpServletRequest request,
			@CookieValue(value = "accountName", required = false) String username) {
		Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if (cookie.getName().equals("accountName")) {
	                Optional<Users> userOptional = this.userService.findByname(cookie.getValue());
	                if (userOptional.isPresent()) {
	                    Users user = userOptional.get();
	                    getName(request, model);
	                    model.addAttribute("trademark", new TrademarkDTO());
	                    return "manager/trademark/addTrademark";
	                } else {
	                	return "manager/trademark/addTrademark";
	                }
	            }
	        }
	    }
	    return "redirect:/login";
	}

	@GetMapping("/order")
	public String listOrder(ModelMap model, @CookieValue(value = "accountName", required = false) String username,
	        HttpServletRequest request, HttpServletResponse response) {
	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if (cookie.getName().equals("accountName")) {
	                Optional<Account> accountOptional = this.accountService.findByname(cookie.getValue());
	                if (accountOptional.isPresent()) {
	                    Account account = accountOptional.get();
	                    model.addAttribute("accountName", username);
	                    model.addAttribute("fullname", account.getAccountName());
	                    List<Order> list = this.orderService.listOrder();
	                    model.addAttribute("listOrder", list);
	                    return "manager/order/order";
	                } else {
	                    model.addAttribute("errorMessage", "Account not found.");
	                    return "manager/order/order";
	                }
	            }
	        }
	    }
	    return "redirect:/login";
	}


	@GetMapping("/orderDetail/{id}")
	public String viewOrderdetailsForManager(@PathVariable("id") long id, ModelMap model,
			@CookieValue(value = "accountName", required = false) String username, HttpServletRequest request,
			HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if (cookie.getName().equals("accountName")) {
	                Optional<Account> userOptional = this.accountService.findByname(cookie.getValue());
	                if (userOptional.isPresent()) {
	                    Account user = userOptional.get();
	                    model.addAttribute("accountName", username);
	                    model.addAttribute("fullname", user.getAccountName());
	                    List<OrderDetail> list = this.orderDetailService.findDetailByInvoiceId(id);
	                    List<Product> productorder = new ArrayList<>();
	                    for (OrderDetail orderDetail : list) {
	                        Product ordProduct = productService.findByIdProduct(orderDetail.getProduct().getProductID());
	                        ordProduct.setAmount(orderDetail.getAmount());
	                        productorder.add(ordProduct);
	                    }
	                    model.addAttribute("listOrderDetail", productorder);
	                    return "manager/order/orderDetail";
	                } else {
	                	return "manager/order/orderDetail";
	                }
	            }
	        }
	    }
	    return "redirect:/login";
	}
}
