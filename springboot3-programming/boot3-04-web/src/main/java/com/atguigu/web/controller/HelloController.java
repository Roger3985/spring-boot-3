package com.atguigu.web.controller;

import com.atguigu.web.bean.Person;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/**
 * @author Roger
 * @Description
 * @create 2024-07-28
 */
@Slf4j
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello2() {
        return "hello";
    }

    /**
     * 默認使用新版的 PathPatternParser （路徑匹配器）進行匹配。
     * 不能匹配 ** 在中間的情況，剩下的跟 antPathMatcher(spring 5.3 以前，現已經不是默認使用） 語法兼容。
     * 需要使用 ** 在中間，需要再 properties 中改變路徑匹配策略成：ant_path_matcher 老版策略，注意：path_pattern_parser 是新版策略。
     * @param request
     * @param path
     * @return
     */
    @GetMapping("/a*/b?/{p1:[a-f]+}/**")
    public String hello(HttpServletRequest request, @PathVariable("p1") String path) {
        log.info("路徑變量 p1: {}", path);
        String uri = request.getRequestURI();
        log.info(uri);
        return uri;
    }

    @Autowired // 國際化取消息用的組件
    MessageSource messageSource;

    @GetMapping("/haha")
    public String haha(HttpServletRequest request) {
        Locale locale = request.getLocale();
        // 利用程式碼的方式獲取國際化配置文件中指定的配置項的值
        String login = messageSource.getMessage("login", null, locale);
        return login;
    }

    /**
     * 1. 默認支持把物件寫為 json。因為默認 web 場景導入了 jackson 處理了 json 包，利用 jackson-core。
     * 2. jackson 也支持把資料寫為 xml，需要導入 xml 相關依賴。
     * @return person。
     */
    @GetMapping("/person")
    public Person person(/* @RequestBody Person person */ ) {
        Person person = new Person();
        person.setId(1L);
        person.setUsername("張三");
        person.setEmail("aaa@qq.com");
        person.setAge(18);
        return person;
    }
}
