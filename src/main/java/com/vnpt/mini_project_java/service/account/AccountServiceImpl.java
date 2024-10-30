package com.vnpt.mini_project_java.service.account;

import com.vnpt.mini_project_java.dto.AccountDTO;
import com.vnpt.mini_project_java.dto.LoginDTO;
import com.vnpt.mini_project_java.entity.Account;
import com.vnpt.mini_project_java.response.LoginMesage;
import com.vnpt.mini_project_java.respository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService{
    
	
	@Autowired
    private final AccountRepository accountRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

	private static final String DATE_FORMAT = "yyyy-MM-dd";

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    @Query(value = "SELECT * FROM account  WHERE account_phone = ?", nativeQuery = true)
    public Optional<Account> findByphone(String phone) {
        return accountRepository.findByphone(phone);
    }

    @Override
    public Account findByAccountName(String accountName){
        return accountRepository.findByAccountName(accountName);
    }

    @Override
    public Optional<Account> findByname(String accountName) {
        return accountRepository.findByname(accountName);
    }

    @Override
    public Optional<Account> findById(Long accountID) {
        return accountRepository.findById(accountID);
    }

	@Override
	public String addAccount(AccountDTO accountDTO, MultipartFile image) {

		if (accountRepository.findByAccountName(accountDTO.getAccountName()) != null) {
			throw new RuntimeException("Tài khoản đã tồn tại");
		}

		Account account = new Account();
		account.setAccountID(accountDTO.getAccountID());
		account.setAccountName(accountDTO.getAccountName());
		account.setAccountPass(this.passwordEncoder.encode(accountDTO.getAccountPass()));
		account.setDateOfBirth(LocalDate.parse(accountDTO.getDateOfBirth(),dateTimeFormatter));
		account.setEmail(accountDTO.getEmail());
		account.setUsername(accountDTO.getUsername());
		account.setPhoneNumber(accountDTO.getPhoneNumber());
		account.setLocal(accountDTO.getLocal());

		String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();// xử lý tên file để tránh bị lặp
		Path uploadDir = Paths.get("src/main/resources/static/images");

		if (!Files.exists(uploadDir)) {
			try {
				Files.createDirectories(uploadDir);
			} catch (IOException ex) {
				throw new RuntimeException("Không thể tạo thư mục upload", ex);
			}
		}
		Path filePath = uploadDir.resolve(fileName);
		try {
			image.transferTo(filePath);
		} catch (IOException ex) {
			throw new RuntimeException("Không thể lưu file hình ảnh", ex);
		}
		String imagePath = fileName;
		account.setImage(imagePath);

		accountRepository.save(account);
		
		return account.getAccountName();
	}

	@Override
	public LoginMesage loginAccount(LoginDTO loginDTO) {
			String msg = "";
			
			Account account1 = accountRepository.findByAccountName(loginDTO.getAccountName());
			
			if (account1 != null) {
				String password = loginDTO.getAccountPass();
				String encodedPassword = account1.getAccountPass();
				Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
				System.out.println("Entered password: " + password);
				System.out.println("Encoded password: " + encodedPassword);
				if (isPwdRight) {
					Optional<Account> acccount = accountRepository.findOneByAccountNameAndAccountPass(loginDTO.getAccountName(), encodedPassword);
					if (acccount.isPresent()) {
						Account acc = acccount.get();
						boolean isAdmin = acc.getIsAdmin();
						return new LoginMesage("Login Success", true, isAdmin);
					} else {
						return new LoginMesage("Login Failed", false, false);
					}
				} else {
					return new LoginMesage("Mật khẩu không chính xác.Vui lòng kiểm tra lại!", false, false);
				}
			} else {
				return new LoginMesage("Email hoặc tài khoản đăng nhập không chính xác", false, false);
			}
	}
}	
