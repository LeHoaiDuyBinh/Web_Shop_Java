package com.example.web_shop_ptit.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth/")
public class ProductController {
    @GetMapping("/product")
    public String productPage(){
        return "web_client/product";
    }
}
