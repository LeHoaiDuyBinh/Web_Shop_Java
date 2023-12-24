package com.example.web_shop_ptit.client.controller;

import com.example.web_shop_ptit.client.entity.RegistrationInfo;
import com.example.web_shop_ptit.client.service.EmailService;
import com.example.web_shop_ptit.client.service.RegistrationService;
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
public class ForgotPasswordController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("forgotPassword")
    public String forgotPassword(@RequestParam String email_forget, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String verifyCode = EmailService.randomVerificationCode();
        if (action.equals("forgotPassword")) {
            try {
                EmailService.sendVerificationCode(email_forget, verifyCode);
                System.out.println("Send mail thành công");
                session.setAttribute("verifyCode", verifyCode);
                session.setAttribute("registrationInfo", new RegistrationInfo(email_forget));
                model.addAttribute("message", "Send code successfully");
            }catch (Exception ex) {
                model.addAttribute("message", "Error seding code");
            }
        }
        return "redirect:/auth/confirmChangePassword";
    }

    @PostMapping("confirmChangePassword")
    public String confirmChangePassword(@RequestParam String confirmCode,
                                        @RequestParam String newPassword,
                                        RedirectAttributes redirectAttributes,
                                        HttpServletRequest request) {
        HttpSession session = request.getSession();
        String verifyCode = (String) session.getAttribute("verifyCode");
        RegistrationInfo registrationInfo = (RegistrationInfo) session.getAttribute("registrationInfo");
        System.out.println(verifyCode);
        if (verifyCode != null && registrationInfo != null) {
            if (confirmCode.equals(verifyCode)) {
                registrationInfo.setPassword(newPassword);
                registrationService.updatePassword(registrationInfo.getEmail(), hashPasswordSHA256(newPassword));

                // Clear session attributes
                session.removeAttribute("verifyCode");
                session.removeAttribute("registrationInfo");

                // Redirect to a success page
                redirectAttributes.addFlashAttribute("success", "1");
                return "redirect:/auth/confirmChangePassword";
            } else {
                // Incorrect verification code
                redirectAttributes.addFlashAttribute("error", "2");
                return "redirect:/auth/confirmChangePassword";
            }
        } else {
            // Session attributes are missing, redirect to the forgot password page
            return "redirect:/auth/forgotpassword";
        }
    }

    @RequestMapping("forgotpassword")
    public String forgotPassword(){
        return "web_client/forgotPassword";
    }

    @RequestMapping("confirmChangePassword")
    public String confirmChangePassword(){
        return "web_client/changePassword";
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
}
