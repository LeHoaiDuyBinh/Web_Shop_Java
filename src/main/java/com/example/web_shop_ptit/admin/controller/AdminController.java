package com.example.web_shop_ptit.admin.controller;

import com.example.web_shop_ptit.admin.entity.Admin;
import com.example.web_shop_ptit.admin.service.AdminService;
import com.example.web_shop_ptit.client.entity.RegistrationInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/admin/login")
    public String loginPage() {
        return "web_admin/login";
    }

    @PostMapping("/admin/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        // Assume you have a method in the UserService to validate the login
        Admin admin = adminService.validateLogin(username, password);
        if (admin != null) {
            // Set user information in the session;
            // ẩn mật khẩu
            admin.setPassword("");
            httpSession.setAttribute("adminInfo", admin);
            return "redirect:/admin/category"; // Redirect to the desired page after successful login
        } else {
            // Handle login failure, e.g., show an error message
            model.addAttribute("error", "Invalid email or password");
            return "web_admin/login";
        }
    }
}
