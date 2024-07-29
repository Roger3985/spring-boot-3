package com.atguigu.web.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import java.util.Map;

/**
 * @author 2407009
 * @Description
 * @create 2024-07-29
 */
@Component("Aaa")
public class aaa implements View {

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.getWriter().write("aaa");
    }
}
