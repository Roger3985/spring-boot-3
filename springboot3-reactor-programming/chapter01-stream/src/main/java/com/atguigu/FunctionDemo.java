package com.atguigu;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Roger
 * @Description
 * @create 2024-08-03
 */
public class FunctionDemo {
    public static void main(String[] args) {
        // 1. 定義一個資料提供者
        Supplier<String> supplier = () -> "42";
        // 2. 斷言：驗證是否一個數字
        Predicate<String> isNumber = str -> str.matches("-?\\d+(\\.\\d+)?");
        // 3. 轉換器：把字符串變成數字 類::實例方法（靜態方法）
//        Function<String, Integer> change = str -> Integer.parseInt(str);
        Function<String, Integer> change = Integer::parseInt;
        // 4. 消費者; 打印數字
        Consumer<Integer> consumer = integer -> {
            if (integer % 2 == 0) {
                System.out.println("偶數：" + integer);
            } else {
                System.out.println("奇數：" + integer);
            }
        };

        mymethod(() -> "777",
                str -> str.matches("-?\\d+(\\.\\d+)?"),
                Integer::parseInt,
                System.out::println);

//        System.out.println(supplier.get());
//        System.out.println(isNumber.test("777"));
    }

    private static void mymethod(Supplier<String> supplier,
                                 Predicate<String> isNumber,
                                 Function<String, Integer> change,
                                 Consumer<Integer> consumer) {
        // 串在一起，實現判斷 42 這個字符串是奇數還是偶數
        if (isNumber.test(supplier.get())) {
            // 說明是一個數字
            consumer.accept(change.apply(supplier.get()));
        } else {
            // 說明不是一個數字
            System.out.println("非法的數字");
        }
    }
}
