package com.example.web_shop_ptit.admin.interceptor;


import com.example.web_shop_ptit.admin.model.User;
import com.example.web_shop_ptit.admin.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String loggedInUser = (String) session.getAttribute("loggedInUser");

        // Check if the user is logged in
        if (loggedInUser == null) {
            // Redirect to the login page if not logged in
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return false;
        }

        // User is logged in, continue with the request
        return true;
    }
}

