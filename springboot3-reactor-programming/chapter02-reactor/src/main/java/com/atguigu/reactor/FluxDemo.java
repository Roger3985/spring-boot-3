package com.atguigu.reactor;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import java.io.IOException;
import java.time.Duration;

/**
 * @author Roger
 * @Description
 * @create 2024-08-04
 */
public class FluxDemo {

    /**
     * 響應式編程核心：看懂文檔彈珠圖
     * 信號：正常 / 異常（取消、錯誤...）
     * SignalType:
     *      (1) SUBSCRIBE: 被訂閱
     *      (2) REQUEST: 請求了 N 個元素
     *      (3) CANCEL: 流被取消了
     *      (4) ON_SUBSCRIBE: 在訂閱的時候
     *      (5) ON_NEXT: 在元素到達的時候
     *      (6) ON_ERROR: 流錯誤的時候
     *      (7) ON_COMPLETE: 在流正常完成時
     *      (8) AFTER_TERMINATE: 中斷以後
     *      (9) CURRENT_CONTEXT: 當前上下文
     *      (10) ON_CONTEXT: 感知上下文
     *
     * doOnXxx API 觸發時機
     * 1. doOnNext: 每個資料（流的資料）到達的時候觸發
     * 2. doOnEach: 每個元素（流的資料和信號）到達的時候觸發
     * 3. doOnRequest: 消費者請求流元素的時候
     * 4. doOnError: 流發生錯誤的時候
     * 5. doOnSubscribe: 流被訂閱的時候
     * 6. doOnTerminate: 發送取消 / 異常信號中斷了流
     * 7. doOnCancel: 流被取消
     * 8. doOnDiscard: 流中元素被忽略的時候
     * @param args
     */
    public static void doOn(String[] args) {
        // 關鍵：doOnNext: 表示流中的某個元素到達以後觸發我一個回調
        // doOnXxx 要感知某個流的事件，寫在這個流的後面，新流的前面
        Flux.just(1, 2, 3, 4, 5, 6, 7, 0, 5, 6)
                .doOnNext(integer -> { // 元素到達的時候觸發
                    System.out.println("元素到達：" + integer);
                }) // 1, 2, 3, 4, 5, 6, 7, 0
                .doOnEach(integerSignal -> { // each 封裝的更加詳細
                    System.out.println("doOnEach.." + integerSignal);
                })
                .map(integer -> 10 / integer)
                .doOnError(throwable -> {
                    System.out.println("資料庫已經保存了此異常1：" + throwable.getMessage());
                })
                .doOnNext(integer -> {
                    System.out.println("元素到達哈：" + integer);
                }) // 10, 5, 3...
                .map(integer -> 100 / integer)
                .doOnError(throwable -> {
                    System.out.println("資料庫已經保存了此異常2：" + throwable.getMessage());
                })
                .doOnNext(integer -> {
                    System.out.println("元素到達哈哈：" + integer);
                })
                .subscribe(System.out::println);
    }

    // Mono<Integer>: 只有一個 Integer
    // Flux<Integer>: 有很多個 Integer
    public static void fluxDoOn(String[] args) throws InterruptedException, IOException {
//        Mono<Integer> just = Mono.just(1);
//        just.subscribe(System.out::println);

        // 空流: 鏈式 API 中，下面的操作符，操作的是上面的流。
        // 事件感知：當流發生什麼事情的時候，觸發一個回調，系統調用提前定義好的鉤子函數（Hook [鉤子函數]; doOnXxx;
//        Flux<Integer> empty = Flux.just(1, 2, 3)
//                .doOnComplete(() -> {
//                    System.out.println("流結束了.....");
//                }); // 有一個信號：此時代表完成信號
//        empty.subscribe(System.out::println);

        Flux<Integer> flux = Flux.range(1, 7)
                .delayElements(Duration.ofSeconds(1)) // 元素延遲一秒發出
                .doOnComplete(() -> {
                    System.out.println("流正常結束......");
                })
                .doOnCancel(() -> {
                    System.out.println("流已經被取消......");
                })
                .doOnError(throwable -> {
                    System.out.println("流出錯......" + throwable);
                })
                .doOnNext(integer -> {
                    System.out.println("doOnNext..." + integer);
                });

        flux.subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                System.out.println("訂閱者和發佈者綁定好了: " + subscription);
                request(1); // 背壓
            }

            @Override
            protected void hookOnNext(Integer value) {
                System.out.println("元素到達");
                if (value < 5) {
                    request(1); // 繼續要元素
                    if (value == 3) throw new RuntimeException("哈哈錯誤");
                } else {
                    cancel(); // 取消訂閱
                }
            }

            @Override
            protected void hookOnComplete() {
                System.out.println("資料流結束");
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                System.out.println("資料流出異常了");
            }

            @Override
            protected void hookOnCancel() {
                System.out.println("資料流被取消了");
            }

            @Override
            protected void hookFinally(SignalType type) {
                System.out.println("結束信號......" + type);
                // 正常、異常
                try {
                    // 業務程式碼
                } catch (Exception e) {

                } finally {
                    // 結束
                }
            }
        });

        flux.subscribe(System.out::println);

        Thread.sleep(2000);

        Flux<Integer> range = Flux.range(1, 7);
        Flux<Integer> filter = range.filter(integer -> integer > 2);
        Flux<Integer> filter1 = range.filter(integer -> integer > 5);

        System.in.read();
    }

    // 測試 Flux
    public void flux() throws IOException {
        // Mono: 0|1 個元素的流
        // Flux: N 個元素的流 N > 1
        // 發佈者發佈資料：：源頭

        // 1. 多元素的流
        Flux<Integer> just = Flux.just(1, 2, 3, 4, 5);

        // 2. 流不消費就沒用; 消費：訂閱
        just.subscribe(element -> System.out.println("element1 = " + element));
        // 一個資料流可以有很多個消費者
        just.subscribe(element -> System.out.println("element2 = " + element));

        // 對於每個消費者來說都是一樣的：廣播模式
        System.out.println("======================");
        Flux<Long> flux = Flux.interval(Duration.ofSeconds(1));// 每秒產生一個從 0 開始的遞增數字
        flux.subscribe(System.out::println);

        System.in.read();
    }
}
