package com.example.web_shop_ptit.client.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ordershistoryitems")
public class OrderHistoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_detail_id")
    private Long historyDetailId;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "size")
    private String size;

    @Column(name = "total_price")
    private Long totalPrice;

    @ManyToOne
    @JoinColumn(name = "order_code", referencedColumnName = "order_code", insertable = false, updatable = false)
    private OrderHistory orderHistory;

    public Long getHistoryDetailId() {
        return historyDetailId;
    }

    public void setHistoryDetailId(Long historyDetailId) {
        this.historyDetailId = historyDetailId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderHistory getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(OrderHistory orderHistory) {
        this.orderHistory = orderHistory;
    }
}
