package com.example.web_shop_ptit.client.entity;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;


public class RegistrationInfo {
    @NotBlank(message = "*Không được để trống")
    @Length(min = 5, max = 30, message = "*Tên phải tên 5 kí tự")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "*Tên không được có chữ số và ký tự đặc biệt")
    private String fullname;

    @NotBlank(message = "*Không được để trống email!")
    @Email(message = "*Không đúng định dạng email!")
    private String email;

    @NotBlank(message = "*Không được để trống")
    @Size(min = 12, message = "*Password ít nhất 12 kí tự")
    private String password;
    @NotBlank(message = "*Không được để trống")
    @Size(min = 12,message = "*Password ít nhất 12 kí tự")
    private String confirmPassword;
    @NotBlank(message = "*Không được để trống")
    @Size(min = 10, max=10, message = "*Số điện thoại phải 10 số")
    @Pattern(regexp = "^[0-9]*$", message = "*Không được có chữ và ký tự đặc biệt trong số điện thoại")
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
