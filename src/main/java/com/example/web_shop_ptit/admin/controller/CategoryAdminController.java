package com.example.web_shop_ptit.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class CategoryAdminController {
    @GetMapping("/category")
    public String categoryPage() {
        // Xử lý logic và trả về tên của view hoặc đường dẫn
        return "web_admin/category";
    }
}
