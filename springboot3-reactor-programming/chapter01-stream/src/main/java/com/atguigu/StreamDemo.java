package com.atguigu;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Roger
 * @Description
 * @create 2024-08-03
 */
public class StreamDemo {
    public static void main(String[] args) {
        // 1. 挑出最大偶數
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // for 循環，埃個遍歷找到偶數，temp = i; 下次找到的偶數和臨時遍歷比較
        int max = 0;
        for (Integer integer : list) {
            if (integer % 2 == 0) {
                // 是偶數，和 max 進行比較交換
                max = integer >= max ? integer : max;
            }
            System.out.println("最大偶數：" + max);
        }

        /*
            流特性：
            (1) 流是 lazy 的，方法就不會被調用

         */

        /*
            2. Stream API:
               (1) 把資料封裝成流; 要到資料流
               (2) 定義流式操作
               (3) 獲取最終結果
         */
        Optional<Integer> max1 = list.stream()
                .filter(element -> {
                    System.out.println("正在 filter：" + element);
                    return element % 2 == 0;
                }) // intermediate operation 中間操作
                .max(Integer::compare);// terminal operation，終止操作 // 過濾出我們想要的值，如果斷言返回 true 就是我們要的

        max1.ifPresentOrElse(
                value -> System.out.println("最大偶數：" + value),
                () -> System.out.println("列表中沒有偶數")
        );

        // 流的三大部分：1. 資料流 2. N個中間操作 3. 一個中止操作
        /*
            1. 資料流：
         */
        // (1) 創建流
        Stream<Integer> stream = Stream.of(1, 2, 3);
        Stream<Integer> concat = Stream.concat(Stream.of(2, 3, 4), stream);
        Stream<Object> build = Stream.builder().add("11").add("22").build();
        // (2) 從集合容器中獲取這個流，List、Set、Map
        List<Integer> integers = List.of(1, 2);
        Stream<Integer> stream1 = integers.stream();

        Set<Integer> integers1 = Set.of(1, 2);
        integers1.stream();

        Map<Object, Object> of = Map.of();
        of.keySet().stream();
        of.values().stream();
    }
}
