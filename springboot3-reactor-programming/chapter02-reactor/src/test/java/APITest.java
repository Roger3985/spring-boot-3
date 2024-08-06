import org.junit.jupiter.api.Test;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @author 2407009
 * @Description
 * @create 2024-08-05
 */
public class APITest {

    @Test
    void sinks() throws InterruptedException, IOException {
        Sinks.many(); // 發送 Flux 資料
        Sinks.one(); // 發送 Mono 資料

        // Sinks: 接收器，資料管道，所有資料通過這個資料往下走
        Sinks.many().unicast(); // 單播: 這個管道只能綁定單個訂閱者(消費者)
        Sinks.many().multicast(); // 多播: 這個管道能綁定多個訂閱者(消費者)
        Sinks.many().replay(); // 重播: 這個管道能夠重放元素

        // 重頭消費，還是訂閱的那一刻消費;

        Sinks.Many<Object> many = Sinks.many()
                        .unicast() // 單播
                .onBackpressureBuffer(new LinkedBlockingQueue<>(5)); // 背壓佇列


        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                many.tryEmitNext("a-" + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        // 發佈者資料重放，底層利用對列進行緩存之前資料
        Sinks.Many<Object> many1 = Sinks.many().replay().limit(3);
        
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                many1.tryEmitNext("a-" + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        Disposable cache = Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1))
                .cache(3) // 緩存元素
                .subscribe(v -> System.out.println("v = " + v));

        Flux<Integer> cache1 = Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1)) // 不調緩存默認就是緩存所有
                .cache(2); // 緩存兩個元素

        cache1.subscribe(); // 緩存元素;


        // 訂閱
        many.asFlux().subscribe(v -> System.out.println("v: " + v));

        System.in.read();
    }

    @Test
    void retryAndTimeout() throws IOException {
        Flux.just(1, 2, 3)
                .delayElements(Duration.ofSeconds(3))
                .log()
                .timeout(Duration.ofSeconds(1))
                .retry(3) // 把流從頭到尾重新請求一次，依照次數重複嘗試幾次
                .onErrorReturn(2)
                .map(i -> i + "haha")
                .log()
                .subscribe();

        System.in.read();
    }

    void createOrder() {
        // 1. 較驗訂單，價格有問題
        // 只需要敲正確的業務代碼，所有的業務異常，全部拋出我們自定義的異常，由全局異常處理器進行統一處理
    }

    /*
        默認: 錯誤是一種中斷行為
        ->　subscribe: 消費者可以感知 正常元素 與 流發生的錯誤 catch
        更多的錯誤處理
        java 錯誤處理
     */
    @Test
    void error() throws IOException {
//        Flux.just(1, 2, 3)
//                .map(i -> "100 / " + i + " = " + (100 / i)) // this triggers on error with 0
//                .onErrorReturn("Divided by zero :(")
//                .subscribe(v -> System.out.println("v = " + v),
//                           error -> System.out.println("error" + error),
//                           () -> System.out.println("流結束")); // error handling example
//
//        Flux<String> map = Flux.just(1, 2, 0, 4)
//                .map(i -> "100 / " + i + " = " + (100 / i));
//        map.onErrorResume(error -> Mono.just("hehe-777"))
//                .subscribe(v -> System.out.println("v = " + v),
//                        error -> System.out.println("error" + error),
//                        () -> System.out.println("流結束"));
//
//        Flux.just(1, 2, 0, 4)
//                .map(i -> "100 / " + i + " = " + (100 / i))
//                .onErrorResume(error -> Flux.error(new BusinessException(error.getMessage() + "炸了")))
//                        .subscribe(v -> System.out.println("v = " + v),
//                                error -> System.out.println("error" + error),
//                                () -> System.out.println("流結束"));
//
//        Flux.just(1, 2, 0, 4)
//                .map(i -> "100 / " + i + " = " + (100 / i))
//                .onErrorMap(error -> new BusinessException(error.getMessage()))
//                .doFinally(signalType -> {
//                    System.out.println("流信號: " + signalType);
//                })
//                .subscribe(v -> System.out.println("v = " + v),
//                        error -> System.out.println("error" + error),
//                        () -> System.out.println("流結束"));
//
//        Flux.just(1, 2, 3, 4, 5, 0)
//                .map(i -> 10 / i)
//                .onErrorContinue((error, value) -> {
//                    System.out.println("error: " + error);
//                    System.out.println("value: " + value);
//                    System.out.println("發現有" + value + "有問題了，繼續執行其他的，我會記錄這個問題");
//                }) // 發生錯誤繼續
//                .subscribe(v -> System.out.println("v = " + v),
//                        error -> System.out.println("error" + error));

        Flux.interval(Duration.ofSeconds(1))
                .map(i -> 10 / (i - 10))
                .onErrorStop() // 錯誤後停止流，源頭中斷
                // .onErrorComplete() // 錯誤結束信號，替換為正常結束信號
                .subscribe(v -> System.out.println("v = " + v),
                        error -> System.out.println("error" + error),
                        () -> System.out.println("流正常結束"));

        System.in.read();
    }

    class BusinessException extends RuntimeException {
        public BusinessException(String msg) {
            super(msg);
        }
    }

    Mono<String> haha(Throwable throwable) {
        if (throwable.getClass() == NullPointerException.class) {

        }

        return Mono.just("哈哈" + throwable.getMessage());
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
