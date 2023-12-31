package com.example.web_shop_ptit.admin.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.io.Serializable;

@Entity
@Table(name = "productsizes")
public class ProductSizeManagement {
    @EmbeddedId
    private ProductSizeManagementId id;

    @Column(name = "quantity")
    private Long quantity;

    @Embeddable
    public static class ProductSizeManagementId implements Serializable {
        @ManyToOne
        @JoinColumn(name = "product_code")
        private ProductManagement product;

        @Column(name = "size")
        private String size;

        // Constructors, getters, and setters

        public ProductManagement getProduct() {
            return product;
        }

        public void setProduct(ProductManagement product) {
            this.product = product;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }
    }

    public ProductSizeManagementId getId() {
        return id;
    }

    public void setId(ProductSizeManagementId id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
