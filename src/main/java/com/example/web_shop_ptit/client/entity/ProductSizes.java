package com.example.web_shop_ptit.client.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "productsizes")
public class ProductSizes {
    @Id
    @Column(name = "product_code")
    private String productCode;

    @Column(name = "size")
    private Size size;

    @Column(name = "quantity")
    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "product_code", insertable = false, updatable = false)
    private Product product;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
