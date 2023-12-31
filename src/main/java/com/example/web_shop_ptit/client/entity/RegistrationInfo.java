package com.example.web_shop_ptit.client.entity;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;


public class RegistrationInfo {
    @NotBlank
    @Length(min = 5, max = 20)
    private String fullname;

    @NotBlank(message = "*Không được để trống email!")
    @Email(message = "*Không đúng định dạng email!")
    private String email;

    @NotBlank
    @Size(min = 12)
    private String password;
    @NotBlank
    @Size(min = 12)
    private String confirmPassword;
    @NotBlank
    @Size(min = 10, max=10)
    private String phone;

    // constructor, getters, and setters

    public RegistrationInfo() {
    }

    public RegistrationInfo(String email) {
        this.email = email;
    }


    public RegistrationInfo(String fullname, String email, String password, String confirmPassword, String phone) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phone = phone;
    }

    public RegistrationInfo(String fullname, String email, String password, String phone) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
