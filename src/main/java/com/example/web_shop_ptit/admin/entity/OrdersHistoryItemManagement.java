package com.example.web_shop_ptit.admin.entity;

import com.example.web_shop_ptit.admin.entity.OrdersHistoryManagement;
import jakarta.persistence.*;


@Entity
@Table(name = "ordershistoryitems")
public class OrdersHistoryItemManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_detail_id")
    private Long historyDetailId;

    public Long getHistoryDetailId() {
        return historyDetailId;
    }

    public void setHistoryDetailId(Long historyDetailId) {
        this.historyDetailId = historyDetailId;
    }

    @Column(name = "product_code", length = 50)
    private String productCode;

    @Column(name = "quantity")
    private Integer  quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "size")
    private SizeManagement size;

    @Column(name = "total_price")
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "order_code")
    private OrdersHistoryManagement orderHistoryManagement;

    public OrdersHistoryManagement getOrderHistoryManagement() {
        return orderHistoryManagement;
    }

    public void setOrderHistoryManagement(OrdersHistoryManagement orderHistoryManagement) {
        this.orderHistoryManagement = orderHistoryManagement;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer    getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer    quantity) {
        this.quantity = quantity;
    }

    public SizeManagement getSize() {
        return size;
    }

    public void setSize(SizeManagement size) {
        this.size = size;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }


}
