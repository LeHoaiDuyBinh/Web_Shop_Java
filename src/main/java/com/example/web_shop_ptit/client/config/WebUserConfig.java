package com.example.web_shop_ptit.client.config;



import com.example.web_shop_ptit.client.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebUserConfig implements WebMvcConfigurer {

    @Autowired
    private UserInterceptor userInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/auth/cart", "/auth/payment", "/auth/informationUser",
                                 "/auth/orderManagement","/auth/orderManagement/**" , "/auth/changePasswordUser"); // Áp dụng cho mọi đường dẫn con của /admin


    }
}
