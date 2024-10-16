package com.vnpt.mini_project_java.service.cart;

import com.vnpt.mini_project_java.respository.AccountRepository;
import com.vnpt.mini_project_java.respository.CartDetailRepository;
import com.vnpt.mini_project_java.respository.CartRepository;
import com.vnpt.mini_project_java.respository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;

    private final AccountRepository accountRepository;

    private final CartDetailRepository cartDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, AccountRepository accountRepository,
                           CartDetailRepository cartDetailRepository) {
        this.cartRepository = cartRepository;
        this.accountRepository = accountRepository;
        this.cartDetailRepository = cartDetailRepository;
    }
}
