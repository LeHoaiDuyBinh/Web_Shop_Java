package com.example.web_shop_ptit.admin.controller;
import com.example.web_shop_ptit.admin.entity.OrdersHistoryItemManagement;
import com.example.web_shop_ptit.admin.entity.OrdersHistoryManagement;
import com.example.web_shop_ptit.admin.service.OrdersHistoryItemsManagementService;
import com.example.web_shop_ptit.admin.service.OrdersHistoryManagementService;
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
    @Autowired
    private OrdersHistoryItemsManagementService ordersHistoryItemsManagementService;
    @GetMapping("/ordersHistory")
    public String ordersHistoryPage(Model model) {

    List<OrdersHistoryManagement> ordersHistory = ordersHistoryManagementService.listAll();

        model.addAttribute("ordersHistory", ordersHistory);
        return "web_admin/ordersHistory";
    }

    @RequestMapping("orderDetails")
    public String showOrderHistoryPage(Model model,
                                       @RequestParam(name = "MaDonCT", required = false) String orderCode) {
            List<OrdersHistoryItemManagement> ordersHistoryItemList = ordersHistoryItemsManagementService.orderDetails(orderCode);
                model.addAttribute("ordersHistoryItemList", ordersHistoryItemList);
        return "web_admin/ordersHistory";
    }

}
