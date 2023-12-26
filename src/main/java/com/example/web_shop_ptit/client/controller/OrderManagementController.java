package com.example.web_shop_ptit.client.controller;


import com.example.web_shop_ptit.client.entity.Category;
import com.example.web_shop_ptit.client.entity.Order;
import com.example.web_shop_ptit.client.entity.OrderHistory;
import com.example.web_shop_ptit.client.entity.RegistrationInfo;
import com.example.web_shop_ptit.client.service.CategoryService;
import com.example.web_shop_ptit.client.service.CustomerService;
import com.example.web_shop_ptit.client.service.OrderHistoryService;
import com.example.web_shop_ptit.client.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/auth/")
public class OrderManagementController {
    @Autowired
    private CategoryService service;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderHistoryService orderHistoryService;

    @RequestMapping("orderManagement")
    public String showOrderManagement(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        RegistrationInfo customerInfo = (RegistrationInfo) session.getAttribute("customerInfor");
        List<Category> listCategory = service.listAll();
        model.addAttribute("listCategorys", listCategory);
        if (customerInfo != null){
            System.out.println("success");
            model.addAttribute("checkSession", "1");

            List<Order> listOrder = Collections.singletonList(orderService.findByEmail(customerInfo.getEmail()));
            List<OrderHistory> listOrderHistory = Collections.singletonList(orderHistoryService.findByEmail(customerInfo.getEmail()));

            System.out.println(customerInfo);
            System.out.println(listOrder.get(0).getAddress());
            System.out.println(listOrderHistory.get(0).getAddress());
            model.addAttribute("CustomerInfor", customerInfo);
            model.addAttribute("listOrders", listOrder);
            model.addAttribute("listOrderHistories", listOrderHistory);

        }else{
            System.out.println("error");
            model.addAttribute("checkSession", "");

        }

        return "web_client/orderManagement";
    }
}
