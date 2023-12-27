package com.example.web_shop_ptit.admin.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authInterceptor)
//                .addPathPatterns("/admin/category", "/admin/product", "/admin/customer", "/admin/detail",
//                                "/admin/order") // Add paths you want to intercept
//                .excludePathPatterns("/admin/login", "/admin/logout");
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/admin/**") // Áp dụng cho mọi đường dẫn con của /admin
                .excludePathPatterns("/admin/login", "/admin/css/**", "/admin/js/**"); // Loại trừ /admin/login, /admin/css và /admin/js
    }
}

