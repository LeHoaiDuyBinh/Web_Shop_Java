package com.example.web_shop_ptit.client.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "shoppingcart")
public class ShoppingCart {
    @Id
    @Column(name = "cart_code")
    private String cartCode;

    @Column(name = "email")
    private String email;

    @OneToOne(mappedBy = "shoppingCart")
    private Customer customer;

    public String getCartCode() {
        return cartCode;
    }

    public void setCartCode(String cartCode) {
        this.cartCode = cartCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


}
