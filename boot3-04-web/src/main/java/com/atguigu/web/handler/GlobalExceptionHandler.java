//package com.atguigu.web.handler;
//
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * @author 2407009
// * @Description
// * @create 2024-07-29
// */
//@ControllerAdvice // 這個類是集中處理所有 @Controller 發生的錯誤
//public class GlobalExceptionHandler {
//
//    /**
//     * 1. @ExceptionHandler 標示一個方法處理錯誤，默認只能處理這個類發生的指定錯誤。
//     * 2. @ControllerAdvice 統一處理所有錯誤。
//     * @param e 異常錯誤。
//     * @return 字串。
//     */
//    @ResponseBody // 物件寫出為 json
//    @ExceptionHandler(Exception.class)
//    public String handleException(Exception e) {
//        return "ohoh~~統一處理, 原因: " + e.getMessage();
//    }
//}
