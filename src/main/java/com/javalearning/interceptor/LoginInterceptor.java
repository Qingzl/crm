package com.javalearning.interceptor;

import com.javalearning.settings.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();
        if ((request.getContextPath()+"/").equals(uri) || uri.contains("login") || uri.endsWith(".png") || uri.endsWith("" +
                ".JPG") || uri.endsWith(".img")  || uri.endsWith(".css") || uri.endsWith(".js") || (session!=null && ((User)session.getAttribute("user"))!=null)){
            return true;
        }
        response.sendRedirect(request.getContextPath()+"/login.jsp");
        return false;
    }
}
