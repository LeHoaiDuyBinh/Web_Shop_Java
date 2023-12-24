package com.example.web_shop_ptit.client.controller;

import com.example.web_shop_ptit.client.entity.Category;
import com.example.web_shop_ptit.client.entity.Customer;
import com.example.web_shop_ptit.client.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/auth/")
public class LoginUserController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("login")
    public String loginPage(){
        List<Customer> listCustomer = CustomerService.listAll();
        for (Customer customer : listCustomer) {
            System.out.println(customer.getEmail() + customer.getPassword());
        }
        return "web_client/login";
    }

    @PostMapping("login")
    public String login(String email, String password, Model model) {
        boolean checkLogin = customerService.checkLogin(email, password);
        if(checkLogin) {
            return "web_client/home";
        }else{
            model.addAttribute("error", "Invalid email or password");
            return "web_client/login";
        }
    }
}
