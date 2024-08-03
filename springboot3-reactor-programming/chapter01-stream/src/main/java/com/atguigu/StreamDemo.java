package com.atguigu;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Roger
 * @Description
 * @create 2024-08-03
 */
public class StreamDemo {

    private static String[] buffer = new String[1];

    public static void main(String[] args) {
        StreamDemo demo = new StreamDemo();
        System.out.println("111");
        demo.a();
        // b 要消費資料
        // 以下方法使用異步（非同步）處理
        new Thread(() -> {
            demo.b("aa"); // b 也可以進行失敗重試
        }).start();
        System.out.println("222");
    }

    public void a() {
        String a = "aaa";
        System.out.println("a 做完事...");
        buffer[0] = a; // 模擬消息佇列

        // 引入一個緩衝區，引入消息佇列，就能實現全系統，全異步，不阻塞，不等待，實時響應
    }

    public void b(String arg) {
        arg = buffer[0];
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("哈哈：" + arg);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Person {
        private String name;
        private String gender;
        private Integer age;
    }

    public static void a(String[] args) {
        List<Person> list = List.of(
                new Person("雷 豐陽", "男", 18),
                new Person("王 五", "男", 20),
                new Person("趙 六", "男", 22),
                new Person("王 七", "男", 33),
                new Person("雷 二", "男", 18));

        Map<String, List<Person>> collect = list.stream()
                .filter(s -> s.age > 15)
                .collect(Collectors.groupingBy(t -> t.gender));

        // Flow

        System.out.println(collect);

        // 迭代器模式
        for (Person person : list) {
            // 1. 迭代速度取決於資料量
            // 2. 資料來得有容器緩衝
            System.out.println(person);
        }

        // 背壓：背向壓力：消費者根據自己的能力逐個處理
        // 正壓：正向壓力：資料的生產者給消費者壓力;
//        list.stream()
//                .filter(a -> {
//                    System.out.println("aaa");
//                    return a >
//                })

    }

    public static void aa(String[] args) {
        List<Person> list = List.of(
                new Person("雷 豐陽", "男", 18),
                new Person("王 五", "男", 20),
                new Person("趙 六", "男", 22),
                new Person("王 七", "男", 33),
                new Person("雷 二", "男", 18));

        // 1. 挑出年齡大於 18 的人 // 拿到集合流其實就是拿到集合的深拷貝的值，流的所有操作都是流的元素引用
        // filter、map、flatMap 流裡面的每一個元素都完整走一個流水線，才能輪到下一個元素;
        // 第一個元素流經所有的管道處理後，下一個元素才能繼續執行完整管道流程
        // 聲明式：基於事件機制的回調
        list.stream()
                .filter(person -> { // 程序員不自己調用，發生這件事情的時候系統調用
                    // System.out.println("filter: " + person.hashCode());
                    return person.age > 18;
                }) // 挑出大於 18; person 流
                .peek(person -> System.out.println("filter peek: " + person))
                .map(person -> {
                    // System.out.println("Person: " + person.hashCode());
                    return person.getName();
                }) // 拿到所有人的姓名
                .peek(element -> System.out.println("map peek: " + element))
                .flatMap(element -> {
                    String[] s = element.split(" ");
                    return Arrays.stream(s);
                })
                .distinct() // 去重複
                .limit(3) // 設上限制
                .sorted(String::compareTo) // 排序 String 排序器
                // .forEach(System.out::println);
                .forEach(e -> {
                    System.out.println("元素: " + e);
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException ex) {
//                        throw new RuntimeException(ex);
//                    }
                });

        // distinct sorted peek limit skip takeWhile

        System.out.println("=============================");


        List<Integer> collect = List.of(1, 2, 3, 4, 5, 6)
                .stream()
                .filter(i -> i > 2) // 無條件遍歷流中的每一個元素
                .collect(Collectors.toList());
        System.out.println(collect);

        List<Integer> collect1 = List.of(1, 2, 3, 4, 5, 6)
                .stream()
                .takeWhile(i -> i > 2) // 當滿足條件，拿到這個元素，不滿足直接結束流操作
                .collect(Collectors.toList());
        System.out.println(collect1);

        // filter、map、flatMap、takeWhile...
    }

    public static void aaa(String[] args) {
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
        // List aaa = new ArrayList();

        Collection<Object> objects = Collections.synchronizedCollection(new ArrayList<>());

        // 有狀態資料將產生併發安全問題。千萬不要這麼寫？
        // 流的所有操作都是無狀態的; 資料狀態僅在此函數內有效，不溢出至函數外
        long count = Stream.of(1, 2, 3, 4, 5)
                .parallel() // intermediate operation. 併發流出現問題請使用鎖來解決此問題(synchronized)
                .filter(i -> {
                    synchronized (Object.class) {
                        // objects.add(i);
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
