package com.vnpt.mini_project_java.restcontroller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vnpt.mini_project_java.dto.LoginDTO;
import com.vnpt.mini_project_java.dto.LoginResponseDTO;
import com.vnpt.mini_project_java.entity.Account;
import com.vnpt.mini_project_java.entity.Users;
import com.vnpt.mini_project_java.respository.AccountRepository;
import com.vnpt.mini_project_java.respository.UserRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping(path = "/api/v1/", produces = "application/json")
@CrossOrigin(origins = "*")
public class LoginRestController {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
		String phoneNumber = loginDTO.getPhoneNumber();
        String accountPass = loginDTO.getAccountPass();
        String phone = loginDTO.getPhone();
        String userpassword = loginDTO.getUserPass();
        Optional<Account> account = accountRepository.findByPhoneNumberAndAccountPass(phoneNumber, accountPass);
        if (account != null) {
            LoginResponseDTO responseDTO = new LoginResponseDTO();
            return ResponseEntity.ok(responseDTO);
        }
        
        Optional<Users> user = userRepository.findByPhoneAndUserPass(phoneNumber, userpassword);
        if (user != null) {
            LoginResponseDTO responseDTO = new LoginResponseDTO();
            responseDTO.setSuccess(true);
            responseDTO.setMessage("Login successful, redirecting to admin page.");
            responseDTO.setRedirectPage("/admin");
            responseDTO.setAccountName(user.get().getAccountName());
            return ResponseEntity.ok(responseDTO);
        }
        LoginResponseDTO responseDTO = new LoginResponseDTO();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
	}
}
	
