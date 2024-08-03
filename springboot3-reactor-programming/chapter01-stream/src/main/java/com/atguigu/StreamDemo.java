package com.atguigu;


import java.util.*;
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

        // :: 冒號引用（全類名::方法）

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

        System.out.println("主執行緒：" + Thread.currentThread());

        // 流是並發還是不並發？和 for 有啥區別？ // 流也是用 for 循環挨個處理 默認不併發，但也可以是併發的
        // 併發之後需要解決多執行續安全問題
        List aaa = new ArrayList();

        // 有狀態資料將產生併發安全問題。千萬不要這麼寫？
        // 流的所有操作都是無狀態的; 資料狀態僅在此函數內有效，不溢出至函數外
        long count = Stream.of(1, 2, 3, 4, 5)
                .parallel() // intermediate operation. 併發流出現問題請使用鎖來解決此問題(synchronized)
                .filter(i -> {
                    synchronized (Object.class) {
                        System.out.println("Filter 執行緒：" + Thread.currentThread());
                        System.out.println("正在 Filter：" + i);
                        // aaa.add(i);
                        return i > 2;
                    }
                }) // intermediate operation.
                .count(); // terminal operation .

        System.out.println(count);
    }
}
