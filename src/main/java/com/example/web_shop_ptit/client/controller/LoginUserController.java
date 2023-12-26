package com.example.web_shop_ptit.client.controller;

import com.example.web_shop_ptit.client.entity.Category;
import com.example.web_shop_ptit.client.entity.Customer;
import com.example.web_shop_ptit.client.entity.RegistrationInfo;
import com.example.web_shop_ptit.client.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/auth/")
public class LoginUserController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("login")
    public String loginPage(){
        List<Customer> listCustomer = CustomerService.listAll();
//        for (Customer customer : listCustomer) {
//            System.out.println(customer.getEmail() + customer.getPassword());
//        }
        return "web_client/login";
    }

    @PostMapping("login")
    public String login(@RequestParam String email,@RequestParam String password, Model model, HttpServletRequest request) {
        boolean checkLogin = customerService.checkLogin(email, password);
        if(checkLogin) {
            HttpSession session = request.getSession();
            Customer customer = customerService.findByEmail(email);
            session.setAttribute("customerInfor", new RegistrationInfo(customer.getFullName(), customer.getEmail(), customer.getPassword(), customer.getPhoneNumber()));
            return "redirect:/";
        }else{
            model.addAttribute("error", "Invalid email or password");
            return "web_client/login";
        }
    }
}