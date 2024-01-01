package com.example.web_shop_ptit.admin.service;

import com.example.web_shop_ptit.admin.entity.*;
import com.example.web_shop_ptit.admin.exception.SaveOrderHistoryException;
import com.example.web_shop_ptit.admin.repository.OrdersHistoryManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OrdersHistoryManagementService {
    @Autowired
    private OrdersHistoryManagementRepository repo;

    public List<OrdersHistoryManagement> listAll() {
        return (List<OrdersHistoryManagement>) repo.findAll();
    }
    public OrdersHistoryManagement getOrdersHistoryByOrderCode(String MaSanPham) {
        List<OrdersHistoryManagement> resultList = repo.findByOrderCode(MaSanPham);
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Transactional
    public void saveOrderHistory(OrderManagement order) {
        try {
            if (order != null){
                OrdersHistoryManagement orderHistory = new OrdersHistoryManagement();
                orderHistory.setOrderCode(order.getOrderCode());
                orderHistory.setOrderDate(order.getOrderDate());
                orderHistory.setAddress(order.getAddress());
                orderHistory.setEmail(order.getCustomer().getEmail());
                orderHistory.setPhone(order.getCustomer().getPhone());
                if(Objects.equals(order.getState(), "delivered"))
                    orderHistory.setState(OrdersState.delivered);
                else if(Objects.equals(order.getState(), "cancelled"))
                    orderHistory.setState(OrdersState.cancelled);
                orderHistory.setTotalPrice(Double.valueOf(order.getTotalPrice()));
                List<OrdersHistoryItemManagement> ordersHistoryItemManagements = new ArrayList<>();
                for(OrderItemsManagement o : order.getOrderItemList()){
                    OrdersHistoryItemManagement ohi = new OrdersHistoryItemManagement();
                    ohi.setOrderHistoryManagement(orderHistory);
                    ohi.setProductCode(o.getProduct().getProductCode());
                    if(Objects.equals(o.getSize(), "S"))
                        ohi.setSize(SizeManagement.S);
                    else if(Objects.equals(o.getSize(), "M"))
                        ohi.setSize(SizeManagement.M);
                    else if(Objects.equals(o.getSize(), "L"))
                        ohi.setSize(SizeManagement.L);
                    else if(Objects.equals(o.getSize(), "XL"))
                        ohi.setSize(SizeManagement.XL);
                    else
                        ohi.setSize(SizeManagement.XXL);
                    ohi.setQuantity(o.getQuantity());
                    ohi.setTotalPrice(Double.valueOf(o.getTotalPrice()));
                    ordersHistoryItemManagements.add(ohi);
                }
                orderHistory.setOrderHistoryItemslist(ordersHistoryItemManagements);
                if(order.getPayment() != null){
                    orderHistory.setPaymentCode(order.getPayment().getPaymentCode());
                    orderHistory.setPaymentDate(order.getPayment().getPaymentDate());
                    if(order.getPayment().getType() != null){
                        orderHistory.setPaymentType(PaymentType.cash);
                    }
                    else{
                        if(Objects.equals(order.getPayment().getType(), "cash")){
                            orderHistory.setPaymentType(PaymentType.cash);
                        }
                        else{
                            orderHistory.setPaymentType(PaymentType.bank_transfer);
                        }
                    }
                    orderHistory.setTotalPrice(Double.valueOf(order.getTotalPrice()));
                }
                repo.save(orderHistory);
            }
        }
        catch (Exception e){
            throw new SaveOrderHistoryException(e.getMessage());
        }
    }
}
