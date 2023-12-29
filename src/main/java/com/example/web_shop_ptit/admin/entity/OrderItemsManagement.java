package com.example.web_shop_ptit.admin.entity;

import jakarta.persistence.*;

@Entity
@Table(name="orderitems")
public class OrderItemsManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private int orderItemsId;

    @ManyToOne
    @JoinColumn(name = "product_code")
    private ProductManagement product;

    @ManyToOne
    @JoinColumn(name = "order_code")
    private OrderManagement order;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "size")
    private String size;

    @Column(name = "total_price")
    private Long totalPrice;

    public int getOrderItemsId() {
        return orderItemsId;
    }

    public void setOrderItemsId(int orderItemsId) {
        this.orderItemsId = orderItemsId;
    }

    public ProductManagement getProduct() {
        return product;
    }

    public void setProduct(ProductManagement product) {
        this.product = product;
    }

    public OrderManagement getOrder() {
        return order;
    }

    public void setOrder(OrderManagement order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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
}
