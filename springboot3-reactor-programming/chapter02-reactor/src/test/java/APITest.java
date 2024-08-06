import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @author 2407009
 * @Description
 * @create 2024-08-05
 */
public class APITest {

    /*
        默認: 錯誤是一種中斷行為
        ->　subscribe: 消費者可以感知 正常元素 與 流發生的錯誤 catch
        更多的錯誤處理
        java 錯誤處理
     */
    @Test
    void error() {
        Flux.just(1, 2, 3)
                .map(i -> "100 / " + i + " = " + (100 / i)) // this triggers on error with 0
                .onErrorReturn("Divided by zero :(")
                .subscribe(v -> System.out.println("v = " + v),
                           error -> System.out.println("error" + error),
                           () -> System.out.println("流結束")); // error handling example
    }

    /**
     * zip: 無法結隊的元素會被忽略
     * 最多支持8流壓縮
     */
    @Test
    void zip() {
        // Tuple: 陣列
        // Tuple2: <Integer, Integer>
        Flux.just(1, 2, 3)
                .zipWith(Flux.just(4, 5, 6))
//                .map(tuple -> {
//                    Integer t1 = tuple.getT1(); // 陣列中的第一個元素
//                    Integer t2 = tuple.getT2 (); // 陣列中的第二個元素
//                })
                .log()
                .subscribe();
    }

    /**
     * concat: 連接；A 流，所有元素和 B 流所有元素拼接
     * merge: 合併：A 流，所有元素和 B 流所有元素按照時間合併
     * mergeWith
     * mergeSequential：按照哪個流先發元素排隊
     */
    @Test
    void merge() throws IOException {
        Flux.merge(
                Flux.just(1, 2, 3).delayElements(Duration.ofSeconds(1)),
                Flux.just("a", "b").delayElements(Duration.ofMillis(1500)),
                Flux.just("haha", "hehe", "heihei", "xixi® ").delayElements(Duration.ofMillis(800)))
                .log()
                .subscribe();

        Flux.just(1, 2, 3).mergeWith(Flux.just(4, 5, 6));

        System.in.read();
    }

    /**
     * defaultIfEmpty：靜態兜底資料
     * switchIfEmpty：空轉換；調用動態兜底方法；返回兜底資料
     */
    @Test
    void empty() {
        // Mono.just(null); // 流裡面有一個 null 值元素
        // Mono.empty(); // 流裡面沒有元素，只有一個完成信號/結束信號
        haha()
                .defaultIfEmpty("x") // 如果發佈者元素為 null，指定默認值，否則用發佈者的值
                .subscribe(v -> System.out.println("v = " + v));
    }

    Mono<String> haha() {
        return Mono.just("a");
    }


    /**
     * transform
     * transformdefer
     */
    @Test
    void transform() { // 把流變形成新資料
        AtomicInteger atomic = new AtomicInteger();

        Flux<String> flux = Flux.just("a", "b", "c")
                .transform(values -> {
                    // ++atomic
                    if (atomic.incrementAndGet() == 1) {
                        // 如果是: 第一次調用，老流中的所有元素轉成大寫
                        return values.map(String::toUpperCase);
                    } else {
                        // 如果不是第一次調用，原封不動返回
                        return values;
                    }
                });

        // transform 無 defer，不會共享外部變量的值 無狀態轉換；原理，無論多少訂閱者，transform 只執行一次
        // transform 有 defer，會共享外部變量的值   有狀態轉換；原理，無論多少訂閱者，每個訂閱者 transform 都會執行一次
        flux.subscribe(v -> System.out.println("訂閱者1: v = " + v));
        flux.subscribe(v -> System.out.println("訂閱者2: v = " + v));
    }

    /**
     * onSubscribe: 流被訂閱
     * request(unbounded): 請求無限資料
     * onNext(2): 每個資料到達
     * onNext(4): 每個資料到達
     * onComplete: 流結束
     * concatMap: 一個元素可以變很多單個 (對於元素類型無限制)
     *  - concat: Flux.concat;靜態調用
     *  - concatWith: 連接的流要跟老流中的元素類型要一樣。
     */
    @Test
    void concatMap() {

        Flux.just(1, 2)
                        .concatWith(Flux.just(4, 5, 6))
                                .log()
                                        .subscribe();

        // 連接
        Flux.concat(Flux.just(1, 2), Flux.just("h", "j"), Flux.just("haha", "hehe"))
                        .log()
                                .subscribe();

        // Mono、Flux: 資料的發布者
        Flux.just(1, 2)
                .concatMap(s -> {
                    return Flux.just(s + "-> a", 1);
                })
                .log()
                .subscribe();
    }

    @Test // 扁平化
    void flatMap() {
        Flux.just("zhang san", "li si")
                .flatMap(v -> {
                    String[] s = v.split(" ");
                    return Flux.fromArray(s); // 把資料包裝成多元素流
                }) // 兩個人的名字，按照空格區分，打印出所有的姓名
                .log()
                .subscribe();
    }

    @Test
    void filter() {
        Stream.of(1, 2, 3, 4);

        Flux.just(1, 2, 3, 4) // 流發布者
                .log() // 1, 2, 3, 4
                .filter(s -> s % 2 == 0) // 過濾偶數，消費上面的流，request(1)
                .log() // 2, 4
                .subscribe(); // 最終消費者; request(unbounded)
    }
}
