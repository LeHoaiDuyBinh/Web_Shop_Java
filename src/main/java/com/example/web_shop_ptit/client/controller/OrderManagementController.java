package com.example.web_shop_ptit.client.controller;


import com.example.web_shop_ptit.client.entity.*;
import com.example.web_shop_ptit.client.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
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

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderHistoryItemService orderHistoryItemService;

    @Autowired
    private ProductService productService;

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
            List<OrderHistory> listOrderHistory = new ArrayList<>();
            if (orderHistoryService.findByEmail(customerInfo.getEmail()) != null){
                listOrderHistory = Collections.singletonList(orderHistoryService.findByEmail(customerInfo.getEmail()));
            } else{
               listOrderHistory = null;
            }


            model.addAttribute("CustomerInfor", customerInfo);
            model.addAttribute("listOrders", listOrder);
            model.addAttribute("listOrderHistories", listOrderHistory);


        }else{
            System.out.println("error");
            model.addAttribute("checkSession", "");

        }

        return "web_client/orderManagement";
    }

    @GetMapping("orderManagement/{slug}")
    public String getPageOrderDetail(@PathVariable String slug, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        RegistrationInfo customerInfo = (RegistrationInfo) session.getAttribute("customerInfor");
        List<Category> listCategory = service.listAll();
        model.addAttribute("listCategorys", listCategory);

        if (customerInfo != null) {
            System.out.println("success");
            model.addAttribute("checkSession", "1");

            Order order = orderService.findByOrderCodeAndEmail(slug, customerInfo.getEmail());
            OrderHistory orderHistory = orderHistoryService.findByOrderCodeAndEmail(slug, customerInfo.getEmail());
            List<Product> listProduct = productService.listAll();
//            List<OrderItem> listOrderItem = Collections.singletonList(orderItemService.findByOrderCode(slug));

            System.out.println(customerInfo);
            model.addAttribute("CustomerInfor", customerInfo);
            model.addAttribute("Order", order);
            model.addAttribute("OrderHistory", orderHistory);
            model.addAttribute("ListProducts", listProduct);
//            model.addAttribute("listOrderItem", listOrderItem);
//            model.addAttribute("listOrderHistoryItem", listOrderHistoryItem);
        }else{
            System.out.println("error");
            model.addAttribute("checkSession", "");
        }
        return "web_client/detailOrder";
    }
}
