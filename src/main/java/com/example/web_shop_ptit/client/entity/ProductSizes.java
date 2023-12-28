package com.example.web_shop_ptit.client.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "productsizes")
public class ProductSizes {

    @EmbeddedId
    private ProductSizeId id;

    @Column(name = "quantity")
    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "product_code", insertable = false, updatable = false)
    private Product product;

    public ProductSizeId getId() {
        return id;
    }

    public void setId(ProductSizeId id) {
        this.id = id;
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

    @Embeddable
    public static class ProductSizeId implements Serializable {
        @Column(name = "product_code")
        private String productCode;
        @Enumerated(EnumType.STRING)
        @Column(name = "size")
        private Size size;

        public String getProductCode() {
            return productCode;
        }

        public Size getSize() {
            return size;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public void setSize(Size size) {
            this.size = size;
        }
    }


}
