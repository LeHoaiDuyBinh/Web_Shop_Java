package com.example.web_shop_ptit.admin.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;


@Entity
@Table(name = "Customers")
public class CustomerManagement {
    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "password", nullable = false)
    private String password;
    @NotBlank(message = "Vui lòng nhập tên khách hàng")
    @Pattern(regexp = "^[a-zA-Z].*", message = "Tên khách hàng không chứa kí tự đặc biệt và bắt đầu bằng chữ")
    @Column(name = "full_name", nullable = false)
    private String fullName;


    @NotNull(message = "Vui lòng nhập số điện thoại")
    @Pattern(regexp = "\\d{10}", message = "Số điện thoại phải có 10 chữ số")
    @Column(name = "phone")
    private String phone;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
