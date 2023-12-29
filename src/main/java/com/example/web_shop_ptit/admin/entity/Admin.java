package com.example.web_shop_ptit.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "adminaccounts")
public class Admin {
    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getStatusExpire() {
        return statusExpire;
    }

    public void setStatusExpire(Boolean statusExpire) {
        this.statusExpire = statusExpire;
    }

    @Column(name = "role")
    private String role;

    @Column(name = "status_expire")
    private Boolean statusExpire;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
