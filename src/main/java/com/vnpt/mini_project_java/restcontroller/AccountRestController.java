package com.vnpt.mini_project_java.restcontroller;

import com.vnpt.mini_project_java.dto.AccountDTO;
import com.vnpt.mini_project_java.dto.LoginDTO;
import com.vnpt.mini_project_java.response.LoginMesage;
import com.vnpt.mini_project_java.service.account.AccountService;


import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path="/api/v1/account", produces="application/json")
@CrossOrigin(origins="*") 
public class AccountRestController {
    
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AccountRestController.class);
	
	@Autowired
    private AccountService accountService;
	
	@PostMapping(path = "/save")
    public ResponseEntity<String> saveEmployee(@RequestBody AccountDTO accountDTO){
		 	String id = accountService.addAccount(accountDTO);
	        if (id != null) {
                System.out.println(id + "test");
	            return new ResponseEntity<>("Account registered successfully with ID: " + id, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Registration failed", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
    }
	
	
    @PostMapping(path = "/login")
    public ResponseEntity<LoginMesage> loginEmployee(@RequestBody LoginDTO loginDTO , HttpServletResponse response){
    	LoginMesage  loginResponse = accountService.loginAccount(loginDTO);
    	HttpHeaders headers = new HttpHeaders();
    	if (loginResponse.isSuccess()) {
            Cookie cookie = new Cookie("accountName", loginDTO.getAccountName());
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(24 * 60 * 60);
            response.addCookie(cookie);

            if (loginResponse.isAdmin()) {
            	headers.setLocation(URI.create("/user"));
            } else {
                
                headers.setLocation(URI.create("/admin"));
            }
            logger.info("User {} logged in successfully at {}", loginDTO.getAccountName(), java.time.LocalDateTime.now());
        } else {
            logger.warn("Failed login attempt for user {} at {}", loginDTO.getAccountName(), java.time.LocalDateTime.now());
        }
        ResponseEntity<LoginMesage> entity = new ResponseEntity<>(loginResponse,headers,HttpStatus.OK);
        return entity;
    }
    
}
