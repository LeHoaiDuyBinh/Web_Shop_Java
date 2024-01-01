package com.example.web_shop_ptit.admin.controller;
import com.example.web_shop_ptit.admin.entity.Admin;
import com.example.web_shop_ptit.admin.entity.OrdersHistoryManagement;
import com.example.web_shop_ptit.admin.service.OrdersHistoryManagementService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/")
public class OrdersHistoryAdminController {
    @Autowired
    private OrdersHistoryManagementService ordersHistoryManagementService;

    @GetMapping("/ordersHistory")
    public String ordersHistoryPage(Model model, HttpServletRequest request) {

    List<OrdersHistoryManagement> ordersHistory = ordersHistoryManagementService.listAll();

        model.addAttribute("ordersHistory", ordersHistory);
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("adminInfo");
        model.addAttribute("role", admin.getRole());
        model.addAttribute("name", admin.getUsername());
        return "web_admin/ordersHistory";
    }

}
