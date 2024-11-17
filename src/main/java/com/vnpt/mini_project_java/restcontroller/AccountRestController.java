package com.vnpt.mini_project_java.restcontroller;

import com.vnpt.mini_project_java.dto.AccountDTO;
import com.vnpt.mini_project_java.dto.LoginDTO;
import com.vnpt.mini_project_java.dto.ProductDTO;
import com.vnpt.mini_project_java.entity.Account;
import com.vnpt.mini_project_java.entity.Product;
import com.vnpt.mini_project_java.response.LoginMesage;
import com.vnpt.mini_project_java.service.account.AccountService;


import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.List;


import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(path = "/api/v1/account", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class AccountRestController {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AccountRestController.class);

    @Autowired
    private AccountService accountService;

    private String generateRandomText(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder captchaText = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            captchaText.append(characters.charAt(index));
        }

        return captchaText.toString();
    }

    @GetMapping("/captcha")
    public void generateCaptcha(HttpServletResponse response, HttpSession session) throws IOException {
        int width = 150, height = 50;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        String captchaText = generateRandomText(5);
        session.setAttribute("captcha", captchaText);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(Color.BLUE);

        g.drawString(captchaText, 20, 35);
        g.dispose();

        response.setContentType("image/png");
        ImageIO.write(image, "png", response.getOutputStream());
    }

    @GetMapping("/Listgetall")
    public ResponseEntity<List<AccountDTO>> getList() {
        List<AccountDTO> accountDTOS = accountService.getAllAccountDTO();
        return ResponseEntity.ok(accountDTOS);
    }

    @PostMapping(path = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> addAccount(@ModelAttribute AccountDTO accountDTO, @RequestParam("image") MultipartFile image) {
        try {
            String accountName = accountService.addAccount(accountDTO, image);
            if (accountName == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("message", "Tài khoản đã tồn tại"));
            }
            return ResponseEntity.ok(Collections.singletonMap("message", "Tài khoản đã đăng ký thành công với ID: " + accountName));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Có lỗi xảy ra: " + e.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateAccount(
            @PathVariable("id") Long accountID,
            @ModelAttribute AccountDTO accountDTO,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            accountDTO.setAccountID(accountID);
            accountService.updateAccount(accountID, accountDTO, image);
            return ResponseEntity.ok("Cập nhật tài khoản thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra khi cập nhật tài khoản");
        }
    }

    @GetMapping("/{id}/get")
    public ResponseEntity<AccountDTO> getProductById(@PathVariable(name = "id") Long accountID) {
        Account account = accountService.getAccountById(accountID);
        AccountDTO accountResponse = new AccountDTO(account);
        return ResponseEntity.ok().body(accountResponse);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<LoginMesage> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response, HttpSession session) {

        LoginMesage loginResponse = accountService.loginAccount(loginDTO, session);
        HttpHeaders headers = new HttpHeaders();
        String generatedCaptcha = (String) session.getAttribute("captcha");

        if (generatedCaptcha == null || !generatedCaptcha.equalsIgnoreCase(loginDTO.getCaptcha())) {
            loginResponse.setCaptchaValid(false);
            loginResponse.setMessage("Captcha không hợp lệ. Vui lòng thử lại.");
            return new ResponseEntity<>(loginResponse, HttpStatus.BAD_REQUEST);
        }

        loginResponse.setCaptchaValid(true);

        if (loginResponse.isSuccess()) {

            Cookie cookie = new Cookie("accountName", loginDTO.getAccountName());
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(24 * 60 * 60);
            response.addCookie(cookie);
            if (loginResponse.isAdmin()) {
                headers.setLocation(URI.create("/admin"));
            } else if (loginResponse.isUser()) {
                headers.setLocation(URI.create("/user"));
            } else if (loginResponse.isUserVip()) {
                headers.setLocation(URI.create("/user-vip"));
            } else {
                headers.setLocation(URI.create("/"));
            }
            logger.info("Người dùng đã đăng nhập thành công", loginDTO.getAccountName(), java.time.LocalDateTime.now());
        } else {
            logger.warn("Lần đăng nhập không thành công của người dùng", loginDTO.getAccountName(), java.time.LocalDateTime.now());
        }
        ResponseEntity<LoginMesage> entity = new ResponseEntity<>(loginResponse, headers, HttpStatus.OK);
        return entity;
    }


    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setValue("");
                cookie.setMaxAge(0);
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                cookie.setSecure(false);
                response.addCookie(cookie);
            }
        }
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Đăng xuất thành công");

        return ResponseEntity.ok(responseBody);
    }
}
