package com.example.web_shop_ptit.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class CustomerAdminController {
    @GetMapping("/customer")
    public String customerPage() {
        return "web_admin/customer";
    }
}
