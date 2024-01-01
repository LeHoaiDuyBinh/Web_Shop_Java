package com.example.web_shop_ptit.admin.entity;

import jakarta.persistence.*;
import jakarta.websocket.ClientEndpoint;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="Orders")
public class OrderManagement {
    @Id
    @Column(name="order_code")
    private String orderCode;

    @Column(name="order_date")
    private Date orderDate;
    @Column(name="state")
    private  String state;
    @Column(name="total_price")
    private Long totalPrice;

    @ManyToOne
    @JoinColumn(name = "email")
    private CustomerManagement customer;
    @Column(name="address")
    private String address;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItemsManagement> orderItemList;

    public PaymentManagement getPayment() {
        return payment;
    }

    public void setPayment(PaymentManagement payment) {
        this.payment = payment;
    }

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private PaymentManagement payment;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public CustomerManagement getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerManagement customer) {
        this.customer = customer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderItemsManagement> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItemsManagement> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
