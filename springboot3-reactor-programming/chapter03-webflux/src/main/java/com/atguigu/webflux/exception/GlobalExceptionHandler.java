package com.atguigu.webflux.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 2407009
 * @Description
 * @create 2024-08-07
 */
// 全局異常處理
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ArithmeticException.class)
    public String error(ArithmeticException exception) {
        System.out.println("發生了數學運算異常" + exception);
        return "發生了數學運算異常";
    }
}
