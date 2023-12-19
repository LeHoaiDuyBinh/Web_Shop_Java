package com.example.web_shop_ptit.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class HomeController {
    @GetMapping("")
    public String homePage() {
        return "web_client/home";
    }
}
