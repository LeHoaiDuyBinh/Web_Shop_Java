package com.example.web_shop_ptit.admin.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    // Simulate user authentication
    public boolean validateLogin(String username, String password) {
        // Thực hiện logic xác thực tại đây, ví dụ đơn giản chỉ để minh họa
        // Bạn có thể kiểm tra trong cơ sở dữ liệu, so sánh mật khẩu, v.v.
        return "admin".equals(username) && "password".equals(password);
    }
}
