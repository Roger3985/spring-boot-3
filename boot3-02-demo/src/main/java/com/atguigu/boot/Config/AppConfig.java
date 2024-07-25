package com.atguigu.boot.Config;

//import com.alibaba.druid.FastsqlException;
import com.atguigu.boot.bean.Pig;
import com.atguigu.boot.bean.Sheep;
import com.atguigu.boot.bean.User;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

/**
 * @author Roger
 * @Description
 * @create 2024-07-25
 */
// @Import(FastsqlException.class) // 也可以利用這個把第三方的依賴註冊為 bean。 // 給容器中放指定類型的組件，組件的名字是全類名
// 以下兩種配置類都是一樣的，但可以用來區分兩者主要是哪一種配置類的使用，但背後都是 @Configuration
// @SpringBootConfiguration // 這是一個 Spring 配置類，替代以前的配置文件。配置本身也是容器中的組件。（在當前 SpringBoot 自己撰寫的配置類，可以標註這個，進行區分）
@Configuration // 這是一個普通配置類，替代以前的配置文件。配置本身也是容器中的組件。（通用配置類，可以標註這個，進行區別）
/**
 * EnableConfigurationProperties 功用：
 * 1. 開啟 Sheep 組件綁定。
 * 2. 默認會把這個組件自己放到容器中。
 */
@EnableConfigurationProperties(Sheep.class) // 導入第三方寫好的組件進行屬性綁定
// SpringBoot 默認只掃描自己主程式所在的包。即使組件上標註了 @Component、@ConfigurationProperties 註解，也沒有用。因為組件都掃描不進來。
public class AppConfig {

    @Bean
    @ConfigurationProperties(prefix = "pig")
    public Pig pig() {
        return new Pig(); // 我們自己 new 新 pig
    }

    /**
     * 1. 組件默認是單實例的(singleton)
     * @return User 實例
     */
    @Scope("prototype") // 將作用域配置成多實例(原型模式)// 非 singleton
    @Bean // ("user") 這個代表指定名稱 // 替代以前的 bean 標籤。 組件在容器中的名字默認是方法名，可以直接修改註解的值。
    public User user() {
        User user = new User();
        user.setId(1L);
        user.setName("張三");
        return user;
    }

    /**
     * 第三方依賴註冊成為 Bean
     * @return FastsqlException
     */
//    @Bean
//    public FastsqlException fastsqlException() {
//        return new FastsqlException();
//    }
}
