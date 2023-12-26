package com.example.web_shop_ptit.client.controller;

import com.example.web_shop_ptit.client.entity.RegistrationInfo;
import com.example.web_shop_ptit.client.service.CustomerService;
import com.example.web_shop_ptit.client.service.EmailService;
import com.example.web_shop_ptit.client.service.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.aspectj.bridge.IMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth/")
public class RegisterUserController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("register")
        public String register (@RequestParam String fullname, @RequestParam String email,
                @RequestParam String password, @RequestParam String confirmPassword,
                @RequestParam String phone, Model model, HttpServletRequest request,
                @Valid RegistrationInfo registrationInfo, BindingResult bindingResult){
            HttpSession session = request.getSession();
            String action = request.getParameter("action");
            String verifyCode = EmailService.randomVerificationCode();

            if (bindingResult.hasErrors()) {
                return "web_client/register";
            }

            if (action.equals("register")) {
                if (customerService.isEmailExists(email)) {
                    bindingResult.rejectValue("email", "error.email", "Email này đã được đăng ký");
                    return "web_client/register";
                }else {
                    try {
                        EmailService.sendVerificationCode(email, verifyCode);
                        System.out.println("Send mail thành công");
                        session.setAttribute("verifyCode", verifyCode);
                        session.setAttribute("registrationInfo", new RegistrationInfo(fullname, email, password, phone));
                        model.addAttribute("message", "Send code successfully");
                    } catch (Exception ex) {
                        model.addAttribute("message", "Error seding code");
                    }
                }
            }
        return "redirect:/auth/confirmUser";
        }

    @GetMapping("/register")
    public String register1(@ModelAttribute RegistrationInfo registrationInfo, Model model){
        model.addAttribute("registrationInfo",registrationInfo);
        return "web_client/register";
    }
    @RequestMapping("register")
    public String registerPage() {
        return "web_client/register";
    }


}
