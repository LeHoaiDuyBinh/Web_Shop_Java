package com.example.web_shop_ptit.client.entity;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.RequestMapping;

@Entity
@Table(name = "orderitems")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

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
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_code", referencedColumnName = "product_code", insertable = false, updatable = false)
    private Product product;

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
