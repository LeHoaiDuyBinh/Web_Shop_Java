package com.example.web_shop_ptit.client.entity;


import jakarta.persistence.*;

import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "state")
    private String state;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "email", referencedColumnName = "email", insertable = false, updatable = false)
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private Collection<OrderItem> orderItems;


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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Collection<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Collection<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
