package com.example.web_shop_ptit.client.controller;

import com.example.web_shop_ptit.client.entity.Product;
import com.example.web_shop_ptit.client.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/auth/")
public class ProductController {
    @Autowired private ProductService service;

    @GetMapping("/product")
    public String productPage(Model model){
        List<Product> listProduct = service.listAll();
        model.addAttribute("listProducts", listProduct);
        return "web_client/product";
    }
}
