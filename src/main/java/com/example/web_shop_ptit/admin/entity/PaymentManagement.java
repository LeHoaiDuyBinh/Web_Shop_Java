package com.example.web_shop_ptit.admin.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Payment")
public class PaymentManagement {

    @Id
    @Column(name = "payment_code")
    private String paymentCode;

    @Column(name = "payment_date")
    private Date paymentDate;

    @OneToOne
    @JoinColumn(name = "order_code")
    private OrderManagement order;

    @Column(name = "type")
    private String type;

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

    public OrderManagement getOrder() {
        return order;
    }

    public void setOrder(OrderManagement order) {
        this.order = order;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
