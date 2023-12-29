package com.example.web_shop_ptit.client.controller;

import com.example.web_shop_ptit.client.entity.RegistrationInfo;
import com.example.web_shop_ptit.client.service.RegistrationService;
import com.example.web_shop_ptit.client.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("/auth/")
public class ConfirmUserController {
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("confirmUser")
    public String confirmUser(@RequestParam String code, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String verifyCode = (String) session.getAttribute("verifyCode");
        RegistrationInfo registrationInfo = (RegistrationInfo) session.getAttribute("registrationInfo");

//        System.out.println(verifyCode);
//        System.out.println(registrationInfo.getFullname() + registrationInfo.getEmail());
//        System.out.println(registrationInfo.getPassword());
//        System.out.println(hashPasswordSHA256(registrationInfo.getPassword()));

        if (code.equals(verifyCode) && registrationInfo != null) {
            // Xác minh thành công, lưu thông tin vào cơ sở dữ liệu
            registrationService.saveRegistrationInfo(
                    registrationInfo.getFullname(),
                    registrationInfo.getEmail(),
                    hashPasswordSHA256(registrationInfo.getPassword()),
                    registrationInfo.getPhone()
            );

            String email_hash = modifyEmail(registrationInfo.getEmail());
            email_hash = email_hash + hashPasswordSHA256(registrationInfo.getEmail());
            shoppingCartService.saveShoppingCart(email_hash, registrationInfo.getEmail());

            // Xóa thông tin khỏi session
            session.removeAttribute("verifyCode");
            session.removeAttribute("registrationInfo");
            redirectAttributes.addFlashAttribute("success", "1");
            return "redirect:/auth/confirmUser";
        } else {
            // Xác minh không thành công
            redirectAttributes.addFlashAttribute("error", "2");
            return "redirect:/auth/confirmUser";
        }
    }

    @RequestMapping("confirmUser")
    public String ShowConfirmUser() {
        return "web_client/confirmUser";
    }

    private String hashPasswordSHA256(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Chuyển đổi byte array thành dạng hex string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Xử lý exception nếu cần
            e.printStackTrace();
            return null;
        }
    }

    private static String modifyEmail(String email) {
        // Tìm vị trí của dấu '@'
        int atIndex = email.indexOf('@');

        // Kiểm tra xem có tồn tại dấu '@' không
        if (atIndex != -1) {
            // Cắt chuỗi và thay đổi dấu '@' thành '_'
            String modifiedEmail = email.substring(0, atIndex) + "_";
            return modifiedEmail;
        } else {
            // Trả về địa chỉ email không thay đổi nếu không có dấu '@'
            return email;
        }
    }
}
