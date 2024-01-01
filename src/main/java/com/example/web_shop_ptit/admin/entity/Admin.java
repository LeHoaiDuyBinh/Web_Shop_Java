package com.example.web_shop_ptit.admin.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "adminaccounts")
public class Admin {
    @Id
    @NotBlank(message = "Vui lòng nhập username")
    @Pattern(regexp = "^[a-zA-Z].*", message = "Username không chứa kí tự đặc biệt và bắt đầu bằng chữ")
    @Size(min = 3, message = "Độ dài của username phải từ 3 kí tự trở lên")
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
    @NotEmpty(message = "Không được để trống role")
    @Pattern(regexp = "^(?!admin$).+", message = "Role không thể là amin")
    private String role;

    @Column(name = "status_expire")
    private Boolean statusExpire;

    @Transient
    private String captcha;

    @Transient
    private String hiddenCaptcha;

    @Transient
    private String realCaptcha;

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getHiddenCaptcha() {
        return hiddenCaptcha;
    }

    public void setHiddenCaptcha(String hiddenCaptcha) {
        this.hiddenCaptcha = hiddenCaptcha;
    }

    public String getRealCaptcha() {
        return realCaptcha;
    }

    public void setRealCaptcha(String realCaptcha) {
        this.realCaptcha = realCaptcha;
    }

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
