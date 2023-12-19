package com.example.web_shop_ptit.admin.controller;

import com.example.web_shop_ptit.admin.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession httpSession;

    @GetMapping("/admin/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        // Invalidating the session effectively logs out the user
        session.invalidate();

        return "redirect:/admin/login";
    }

    @GetMapping("/admin/login")
    public String loginPage() {
        return "web_admin/login";
    }

    @PostMapping("/admin/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        // Assume you have a method in the UserService to validate the login
        if (userService.validateLogin(username, password)) {
            // Set user information in the session
            httpSession.setAttribute("loggedInUser", username);
            return "redirect:/admin/category"; // Redirect to the desired page after successful login
        } else {
            // Handle login failure, e.g., show an error message
            return "redirect:/admin/login?error";
        }
    }
}
