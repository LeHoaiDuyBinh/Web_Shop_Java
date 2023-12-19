package com.example.web_shop_ptit.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth/")
public class PaymentController {
    @GetMapping("/payment")
    public String paymentPage() {
        return "web_client/payment";
    }
}
