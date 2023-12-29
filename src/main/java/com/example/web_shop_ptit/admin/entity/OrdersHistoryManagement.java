package com.example.web_shop_ptit.admin.entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "ordershistory")
public class OrdersHistoryManagement {
    @Id
    @Column(name = "order_code", length = 100)
    private String orderCode;

    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Column(name = "payment_code", length = 50)
    private String paymentCode;

    @Column(name = "payment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private OrdersState state;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "address", length = 150)
    private String address;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "total_price")
    private Double totalPrice;

    public Collection<OrdersHistoryItemManagement> getOrdersHistoryItemManagements() {
        return ordersHistoryItemManagements;
    }

    public void setOrdersHistoryItemManagements(Collection<OrdersHistoryItemManagement> ordersHistoryItemManagements) {
        this.ordersHistoryItemManagements = ordersHistoryItemManagements;
    }

    @OneToMany(mappedBy = "orderHistoryManagement")
    private Collection<OrdersHistoryItemManagement> ordersHistoryItemManagements;


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

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public OrdersState getState() {
        return state;
    }

    public void setState(OrdersState state) {
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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

}
