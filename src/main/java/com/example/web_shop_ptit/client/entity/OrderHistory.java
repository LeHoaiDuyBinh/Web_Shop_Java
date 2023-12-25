package com.example.web_shop_ptit.client.entity;


import jakarta.persistence.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "ordershistory")
public class OrderHistory {
    @Id
    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "payment_code")
    private String paymentCode;

    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "state")
    private String state;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "total_price")
    private Long totalPrice;

    @OneToMany(mappedBy = "orderHistory")
    private Collection<OrderHistoryItem> orderHistoryItems;

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

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Collection<OrderHistoryItem> getOrderHistoryItems() {
        return orderHistoryItems;
    }

    public void setOrderHistoryItems(Collection<OrderHistoryItem> orderHistoryItems) {
        this.orderHistoryItems = orderHistoryItems;
    }
}
