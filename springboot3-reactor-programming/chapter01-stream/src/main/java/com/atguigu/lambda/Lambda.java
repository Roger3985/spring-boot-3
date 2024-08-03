package com.atguigu.lambda;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * @author Roger
 * @Description
 * @create 2024-08-03
 */
/*
    函數式介面; 只要是函數式介面就可以用 Lambda 表達式簡化
    函數式介面：介面中有且只有一個未實現的方法，這個介面就叫函數式介面
*/

interface MyInterface {
    int sum(int i, int j);
}

interface MyHaha {
    int haha();
}

@FunctionalInterface // 使用註解，幫我們快速檢查我們寫的介面是否是函數式介面
interface MyHehe {
    int hehe(int i);
    default int heihei() {return 2;}; // 默認實現
}

// 1. 自己寫實現類
class MyInterfaceImpl implements MyInterface {
    @Override
    public int sum(int i, int j) {
        return i + j;
    }
}

public class Lambda {

    public static void main(String[] args) {
        // 聲明一個函數
        BiConsumer<String, String> consumer = (a, b) -> {
            System.out.println("哈哈：" + a + "; 呵呵：" + b);
        };
        consumer.accept("1", "2");

        // 聲明一個函數
        Function<String, Integer> function = (String x) -> Integer.parseInt(x);
        System.out.println(function.apply("2"));

        // 聲明一個函數
        Supplier<String> supplier = () -> UUID.randomUUID().toString();
        String s = supplier.get();
        System.out.println(s);

        BiFunction<String, Integer, Long> biFunction = (a, b) -> 888L;

        Predicate<Integer> even = (t) -> t % 2 == 0;
        System.out.println(even.test(6)); // 正向判斷
        System.out.println(even.negate().test(2)); // 反向判斷
    }

    public static void bbb(String[] args) {
        List<String> names = new ArrayList<String>();
        names.add("Alice");
        names.add("Bob");
        names.add("Charlie");
        names.add("David");

        // 比較器原生寫法
//        Collections.sort(names, new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return o2.compareTo(o1);
//            }
//        });

        // 比較器（簡化寫法）：直接寫函數式介面就方便
        // Collections.sort(names, (o1, o2) -> o2.compareTo(o1));
        // 類::方法; 引用類中的實例方法; 忽略 Lambda 的完整寫法
        Collections.sort(names, String::compareTo);
        System.out.println(names);

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("哈哈哈");
                    }
                }
        ).start();

        Runnable runnable = () -> System.out.println("aaa");

        new Thread(() -> System.out.println("哈哈哈")).start();

        /*
            最佳實戰：
            1. 以後調用某個方法傳入參數，這個參數是一個介面物件，且定義了一個方法，就直接調用 Lambda 簡化寫法即可。
         */
    }

    /**
     * Lambda 簡化函數介面實力創建
     * @param args
     */
    public static void aaa(String[] args) {
        // 1. 自己創建實現類物件
        MyInterface myInterface = new MyInterfaceImpl();
        System.out.println(myInterface.sum(1, 2));

        // 2. 創建匿名實現類（冗余寫法）
        MyInterface myInterface1 = new MyInterface() {
            @Override
            public int sum(int i, int j) {
                return i * i + j * j;
            }
        };
        System.out.println(myInterface1.sum(2, 3));

        // 3. lambda 表達式（語法糖）：參數列表 + 箭頭 + 方法體（完整寫法）
        MyInterface myInterface2 = (int i, int j) -> {
            return i * i + j * j;
        };
        System.out.println(myInterface2.sum(2,3));

        /*
            4. lambda 表達式（語法糖）（簡化寫法）
               (1) 參數類型可以不寫，只寫（參數名），參數變量名隨意定義;
                   參數表最少可以只有一個 ()，或者只有一個參數名
               (2) 方法體如果只有一句話，{} 可以省略
         */

        // (1) 參數位置最少情況
        MyHaha myHaha = () -> {
            return 1;
        };
        MyHehe myHehe = y -> {
            return y * y;
        };

        // (2) 方法體只有一句話 {} 可以省略
        MyHehe myHehe1 = y -> y + 1;
        System.out.println(myHehe1.hehe(7));
        // 以上 Lambda 表達式簡化了實例的創建。

        /*
            總結：
            1. Lambda 表達式：（參數表）-> {方法體}。
            2. 分辨出你的介面是否是函數式介面。（函數式介面就可以使用 Lambda 介面簡化）
         */
    }
}
