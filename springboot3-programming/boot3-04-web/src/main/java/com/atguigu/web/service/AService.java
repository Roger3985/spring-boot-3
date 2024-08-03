package com.atguigu.web.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author 2407009
 * @Description
 * @create 2024-07-29
 */
@Service
public class AService {
    public void a() {
        // 獲取當前請求的路徑
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        // 任意位置隨時通過 RequestContextHolder 獲取當前請求和響應的信息
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();

        String requestURI = request.getRequestURI();
    }
}
