package com.example.web_shop_ptit.admin.controller;

import cn.apiclub.captcha.Captcha;
import com.example.web_shop_ptit.admin.entity.Admin;
import com.example.web_shop_ptit.admin.security.CaptchaBuilder;
import com.example.web_shop_ptit.admin.service.AdminService;
import com.example.web_shop_ptit.client.entity.RegistrationInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private HttpSession httpSession;

    @GetMapping("/admin/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        // huỷ tất cả session
        session.invalidate();

        return "redirect:/admin/login";
    }

    private void getCaptcha(Admin admin) {
        Captcha captcha = CaptchaBuilder.createCaptcha(180, 40);
        admin.setHiddenCaptcha(captcha.getAnswer());
        admin.setCaptcha(""); // value entered by the User
        admin.setRealCaptcha(CaptchaBuilder.encodeCaptcha(captcha));

    }

    @GetMapping("/admin/login")
    public String loginPage(Model model) {
        Admin admin = new Admin();
        getCaptcha(admin);
        model.addAttribute("admin", admin);
        return "web_admin/login";
    }

    @PostMapping("/admin/login")
    public String login(@ModelAttribute Admin admin, Model model) {
        // Assume you have a method in the UserService to validate the login
        Admin adminCheck = adminService.validateLogin(admin.getUsername(), admin.getPassword());
        if(admin.getCaptcha().equals(admin.getHiddenCaptcha())) {
            if (adminCheck != null) {
                // Set user information in the session;
                // ẩn mật khẩu
                adminCheck.setPassword("");
                httpSession.setAttribute("adminInfo", adminCheck);
                return "redirect:/admin/category"; // Redirect to the desired page after successful login
            } else {
                // Handle login failure, e.g., show an error message
                model.addAttribute("error", "Invalid email or password");
                return "web_admin/login";
            }
        }
        else{
            model.addAttribute("error", "Invalid captcha");
            getCaptcha(admin);
            admin.setUsername("");
            model.addAttribute("user", admin);
            return "web_admin/login";
        }
    }
}
