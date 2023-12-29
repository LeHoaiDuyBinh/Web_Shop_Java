package com.example.web_shop_ptit.client.controller;

import cn.apiclub.captcha.Captcha;
import com.example.web_shop_ptit.client.config.CaptchaGenerator;
import com.example.web_shop_ptit.client.entity.Customer;
import com.example.web_shop_ptit.client.entity.RegistrationInfo;
import com.example.web_shop_ptit.client.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/auth/")
public class LoginUserController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("login")
    public String loginPage(Model model) {
        List<Customer> listCustomer = CustomerService.listAll();
//        for (Customer customer : listCustomer) {
//            System.out.println(customer.getEmail() + customer.getPassword());
//        }
        Customer customer = new Customer();
        getCaptcha(customer);
        model.addAttribute("customer", customer);
        return "web_client/login";
    }

    private void getCaptcha(Customer customer) {
        Captcha capcha = CaptchaGenerator.createCaptcha(120, 80);
        customer.setHiddenCaptcha(capcha.getAnswer());
        customer.setCaptcha("");
        customer.setRealCaptcha(CaptchaGenerator.encodeCaptchatoBinary(capcha));
    }

    @PostMapping("login")
    public String login(@RequestParam String email, @RequestParam String password, Model model, HttpServletRequest request,
                        @ModelAttribute Customer customer1) {
        boolean checkLogin = customerService.checkLogin(email, password);
        if (checkLogin && customer1.getCaptcha().equals(customer1.getHiddenCaptcha())) {
            HttpSession session = request.getSession();
            Customer customer = customerService.findByEmail(email);
            session.setAttribute("customerInfor", new RegistrationInfo(customer.getFullName(), customer.getEmail(), customer.getPassword(), customer.getPhoneNumber()));
            return "redirect:/";
        } else if(!customer1.getCaptcha().equals(customer1.getHiddenCaptcha())){
            model.addAttribute("message", "Invalid Captcha");
            getCaptcha(customer1);
            model.addAttribute("customer", customer1);
            return "web_client/login";
        }else {
            getCaptcha(customer1);
            model.addAttribute("customer", customer1);
            model.addAttribute("error", "Invalid email or password");
            return "web_client/login";
        }
    }

}