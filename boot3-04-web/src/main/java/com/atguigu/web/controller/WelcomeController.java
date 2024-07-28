package com.atguigu.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Roger
 * @Description
 * @create 2024-07-29
 */
@Controller // 適合服務端渲染技術，前後不分離模式開始
public class WelcomeController {

    /**
     * 利用模板引擎跳轉到指定視圖頁面。
     * @return welcome 視圖。
     */
    @GetMapping("/welcome")
    public String welcome(@RequestParam("name") String name,
                          Model model) {
        // 模板的邏輯視圖地址
        // 物理視圖 = 前綴 + 邏輯視圖名 + 後綴
        // 真實地址 = classpath:/templates/welcome

        // 把需要給頁面共享的資料放到 model 中
        String text = "<span style='color:red'>" + name + "</span>";
        model.addAttribute("name", text);


        model.addAttribute("name", name);

        // 路徑是動態的
        model.addAttribute("imageUrl", "/4.png");
        // 資料庫查出的樣式
        model.addAttribute("style", "width: 400px");

        model.addAttribute("show", false);
        return "welcome";
    }
}
