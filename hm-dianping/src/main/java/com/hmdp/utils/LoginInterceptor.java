package com.hmdp.utils;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public void afterCompletion(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull Object handler,
                                @Nullable Exception ex) throws Exception {
        UserHolder.removeUser();
    }

    @Override
    public boolean preHandle(HttpServletRequest request
            , @NonNull HttpServletResponse response
            , @NonNull Object handler) throws Exception {
        if (UserHolder.getUser() ==  null){
            response.setStatus(401);
            return false;
        }
        return true;
    }
}
