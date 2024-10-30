package com.vnpt.mini_project_java.restcontroller;

import com.vnpt.mini_project_java.dto.ProductSearchCriteriaDTO;
import com.vnpt.mini_project_java.dto.ProductDTO;
import com.vnpt.mini_project_java.entity.*;
import com.vnpt.mini_project_java.service.account.AccountService;
import com.vnpt.mini_project_java.service.order.OrderService;
import com.vnpt.mini_project_java.service.orderDetail.OrderDetailService;
import com.vnpt.mini_project_java.service.product.ProductService;
import com.vnpt.mini_project_java.spec.ProductSpecifications;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class HomeRestController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private ProductService productService;

	@GetMapping("/index/listProductAjax")
	public ResponseEntity<?> showListProduct() {
		return ResponseEntity.ok(this.productService.getAllProductDTO());
	}

	private void logToConsoleAndFile(String message) {
		Logger logger = LoggerFactory.getLogger(this.getClass());

		System.out.println(message);

		logger.info(message);
	}

	@PostMapping("/index/listProductByIdCategoryFilter/{categoryID}")
	@ResponseBody
	public List<ProductDTO> showListProductByIdCategory(@PathVariable("categoryID") long id) {
		List<Product> productList = this.productService.showListProductByIdCategoryFilter(id);

		List<ProductDTO> productDTOList = new ArrayList<>();

		for (Product product : productList) {
			ProductDTO productDTO = new ProductDTO();
			productDTO.setId(product.getProductID());
			productDTO.setName(product.getProductName());
			productDTO.setDescription(product.getDescription());
			productDTO.setImageBase64(product.getImageBase64());
			productDTO.setPrice(product.getPrice());
			productDTO.setCategoryID(product.getCategory().getCategoryID());
			productDTOList.add(productDTO);
		}

		return productDTOList;

	}

	@GetMapping("/index/listProductPriceDesc")
	public ResponseEntity<List<ProductDTO>> showListProductPriceDesc() {
		List<Product> productList = this.productService.listProductPriceDesc();
		List<ProductDTO> productDTOList = convertToDTOList(productList);

		return ResponseEntity.ok(productDTOList);
	}

	@GetMapping("/index/listProductPriceAsc")
	public ResponseEntity<List<ProductDTO>> showListProductPriceAsc() {

		List<Product> productList = this.productService.listProductPriceAsc();

		List<ProductDTO> productDTOList = convertToDTOList(productList);

		return ResponseEntity.ok(productDTOList);
	}

	private List<ProductDTO> convertToDTOList(List<Product> productList) {

		List<ProductDTO> productDTOList = new ArrayList<>();

		for (Product product : productList) {
			ProductDTO productDTO = new ProductDTO();
			productDTO.setId(product.getProductID());
			productDTO.setName(product.getProductName());
			productDTO.setImageBase64(product.getImageBase64());
			productDTO.setPrice(product.getPrice());

			productDTOList.add(productDTO);
		}
		return productDTOList;
	}

	@GetMapping("/index/listProductNewBest")
	public ResponseEntity<List<ProductDTO>> showListProductNewBest() {
		List<Product> productList = this.productService.listProductNewBest();

		List<ProductDTO> productDTOList = new ArrayList<>();

		for (Product product : productList) {
			ProductDTO productDTO = new ProductDTO();
			productDTO.setId(product.getProductID());
			productDTO.setName(product.getProductName());
			productDTO.setImageBase64(product.getImageBase64());
			productDTO.setPrice(product.getPrice());

			productDTOList.add(productDTO);
		}

		return ResponseEntity.ok(productDTOList);
	}

	@PostMapping("/insertproduct")
	@ResponseBody
	public Product.CartUpdateStatus saveCartToSession(@RequestParam(name = "productID") long productID,
			@RequestParam int amount, HttpSession session, HttpServletRequest request) {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		Product productOrder = this.productService.findByIdProduct(productID);
		if (productOrder == null) {
			return Product.CartUpdateStatus.PRODUCT_NOT_FOUND;
		}
		if (amount <= 0) {
			return Product.CartUpdateStatus.INVALID_AMOUNT;
		}
		List<Product> cart = (List<Product>) session.getAttribute("cart");
		if (cart == null) {
			cart = new ArrayList<>();
			session.setAttribute("cart", cart);
		}
		boolean productFoundInCart = false;
		for (Product item : cart) {
			if (item.getProductID().equals(productOrder.getProductID())) {
				item.setAmount(item.getAmount() + amount);
				productFoundInCart = true;
				break;
			}
		}
		if (!productFoundInCart) {
			productOrder.setAmount(amount);
			cart.add(productOrder);
		}
		session.setAttribute("currentStep", 1);
		Cookie[] cookies = request.getCookies();
		long accountId = -1;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("accountName")) {
					Account account = this.accountService.findByname(cookie.getValue()).orElse(null);
					if (account != null) {
						accountId = account.getAccountID();
					}
					break;
				}
			}
		}
		String username = (accountId != -1) ? accountService.findById(accountId).get().getAccountName() : "unknown";
		String productName = productOrder.getProductName();
		String logMessage = "Người dùng '" + username + "' đã mua " + amount + " Đơn Vị Sản Phẩm '" + productName
				+ "' Vào Gio Hang.";
		logToConsoleAndFile(logMessage);
		return Product.CartUpdateStatus.SUCCESS;
	}
	
	@PostMapping(value = "/updatequantities")
	@ResponseBody
	public String updateQuantity(@RequestParam(name = "productID") long productID,
			@RequestParam(name = "amount") int amount, HttpSession session) {
		if (amount < 0) {
			return "0";
		} else if (amount == 0) {
			List<Product> list = (List<Product>) session.getAttribute("cart");
			for (int i = 0; i < list.size(); i++) {
				if (productID == list.get(i).getProductID()) {
					list.remove(i);
					session.setAttribute("cart", list);
					return "2";
				}
			}
		} else if (session.getAttribute("cart") != null) {
			List<Product> list = (List<Product>) session.getAttribute("cart");
			for (int i = 0; i < list.size(); i++) {
				if (productID == list.get(i).getProductID()) {
					list.get(i).setAmount(amount);
					session.setAttribute("cart", list);
					return "1";
				}
			}
		} else {
			return "0";
		}
		return "0";
	}
	
	@PostMapping(value = "/orders")
	@ResponseBody
	public String orders(HttpServletRequest request, HttpSession session, ModelMap modelMap) {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		Account account = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("accountName")) {
					account = this.accountService.findByname(cookie.getValue()).orElse(null);
					break;
				}
			}
		}
		if (account == null || account.getAccountID() <= 0)
			return "0";
		else {
			if (session.getAttribute("cart") != null) {
				List<Product> list = (List<Product>) session.getAttribute("cart");
				Order order = new Order();

				long millis = System.currentTimeMillis();
				java.sql.Date date = new java.sql.Date(millis);
				LocalDate localDate = date.toLocalDate();

				double orderTotal = 0;
				Set<OrderDetail> setDetail = new HashSet<>();
				for (int i = 0; i < list.size(); i++) {
					orderTotal += list.get(i).getPrice() * list.get(i).getAmount();
					OrderDetail s = new OrderDetail();
					s.setProduct(list.get(i));
					setDetail.add(s);
				}
				order.setOrderDateImport(localDate);
				order.setStatus("CHỜ DUYỆT");
				order.setOrderTotal(orderTotal);
				order.setVendor(account);
				order.setOrderDetails(setDetail);
				orderService.save(order);
				for (int i = 0; i < list.size(); i++) {
					orderTotal += list.get(i).getPrice() * list.get(i).getAmount();
					OrderDetail s = new OrderDetail();
					s.setProduct(list.get(i));
					s.setAmount(list.get(i).getAmount());
					s.setOrder(order);
					s.setPrice(list.get(i).getPrice());
					setDetail.add(s);
					orderDetailService.save(s);
				}
				logger.info("Tài Khoản '{}' đã đặt hàng với tổng giá {} và Mã đơn hàng {}.", account.getAccountName(),
						orderTotal, order.getOrderID());
				session.setAttribute("cart", new ArrayList<>());
			} else {
				return "-1";
			}
		}
		return "1";
	}

	@PostMapping("/cancel-order")
	public ResponseEntity<String> cancelOrder(@RequestParam(name = "orderID") Long orderID) {
		try {
			Order order = orderService.findById(orderID);
			if (order == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy đơn hàng");
			}
			if (!order.getStatus().equals("CHỜ DUYỆT")) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Đơn hàng không thể hủy bỏ");
			}
			order.setStatus("ĐÃ HỦY");
			orderService.save(order);
			return ResponseEntity.ok("Đơn hàng đã được hủy thành công");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi");
		}
	}

	@PostMapping("/search")
	public ResponseEntity<?> searchProducts(@RequestBody ProductSearchCriteriaDTO criteria, Pageable pageable) {
		try {
			Specification<Product> spec = ProductSpecifications.searchByCriteria(criteria);

			Page<Product> products = productService.findAll(spec, pageable);

			Page<ProductDTO> productDTOs = products.map(ProductDTO::new);

			return ResponseEntity.ok(productDTOs);
		} catch (Exception e) {
			System.out.println("Lỗi" + e);
			return ResponseEntity.internalServerError().body("Đã xảy ra lỗi khi xử lý yêu cầu của bạn\n");
		}
	}
	
	private boolean cancelOrderLogic(Order order) {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		try {
			order.setStatus("Đã Hủy");
			orderService.save(order);
			return true;
		} catch (Exception e) {
			logger.error("Lỗi khi thực hiện hủy đơn hàng", e);
			return false;
		}
	}
}