package com.example.web_shop_ptit.admin.controller;

import com.example.web_shop_ptit.admin.entity.Admin;
import com.example.web_shop_ptit.admin.entity.CategoryManagement;
import com.example.web_shop_ptit.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/")
public class StaffController {
    @Autowired
    private AdminService adminService;
    @GetMapping("/staff")
    public String staffPage(Model model) {
        List<Admin> admins = adminService.listAll();

        model.addAttribute("admins", admins);
        return "web_admin/staff";
    }
}
