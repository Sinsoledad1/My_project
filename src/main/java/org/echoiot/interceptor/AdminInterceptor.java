package org.echoiot.interceptor;

import org.echoiot.entity.pojo.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2021/1/21 16:38
 */
@Component
public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("User");
        if (user == null){
            throw new AccessDeniedException("请先登录！");
        }
        System.out.println(user.getRole());
        System.out.println(user.toString());
        if (("SuperAdmin".equals(user.getRole()))||("Admin".equals(user.getRole()))){
            return true;
        }else{
            throw new AuthorizationServiceException("权限不足！");
        }

    }
}
