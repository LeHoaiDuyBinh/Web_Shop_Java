package com.example.web_shop_ptit.client.controller;

import com.example.web_shop_ptit.client.entity.RegistrationInfo;
import com.example.web_shop_ptit.client.service.CustomerService;
import com.example.web_shop_ptit.client.service.EmailService;
import com.example.web_shop_ptit.client.service.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.aspectj.bridge.IMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth/")
public class RegisterUserController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("register")
    public String register(@RequestParam String fullname, @RequestParam String email,
                           @RequestParam String password, @RequestParam String retype_password,
                           @RequestParam String phone, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String verifyCode = EmailService.randomVerificationCode();
        if (action.equals("register")) {
            try {
                EmailService.sendVerificationCode(email, verifyCode);
                System.out.println("Send mail thành công");
                session.setAttribute("verifyCode", verifyCode);
                session.setAttribute("registrationInfo", new RegistrationInfo(fullname, email, password, phone));
                model.addAttribute("message", "Send code successfully");
            }catch (Exception ex) {
                model.addAttribute("message", "Error seding code");
            }
        }
        return "redirect:/auth/confirmUser";
    }

    @RequestMapping("register")
    public String registerPage() {
        return "web_client/register";
    }


}
