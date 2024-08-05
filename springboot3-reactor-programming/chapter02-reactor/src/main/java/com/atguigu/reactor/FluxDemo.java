package com.atguigu.reactor;

import org.reactivestreams.Subscription;
import reactor.core.Disposable;
import reactor.core.publisher.*;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Roger
 * @Description
 * @create 2024-08-04
 */
public class FluxDemo {

    public static void main(String[] args) throws IOException, InterruptedException {
        new FluxDemo().handle();
        // request(N) : 找發佈者請求 N 次資料：總共能得到： N * bufferSize 個資料
        System.in.read();
    }

    public void thread() {
        // 響應式編程: 全異步、消息、事件回調
    }

    public void handle() {
        Flux.range(1, 10)
//                .handle((value, sink) -> {
//                    System.out.println("拿到的值" + value);
//                    sink.next("張三：" +value); // 可以向下發送資料的通道
//                })
                .publishOn(Schedulers.immediate()) // 在哪個執行敘池把這個流的資料跟操作給執行了 // 默認就是使用 Schedulers.immediate
                .log() // 日誌
                .subscribe();

        // publishOn: 改變發布者所在的執行緒池
        // subscribeOn: 改變訂閱者所在執行緒池

        // 調度器:線程池
//        Schedulers.immediate(); // 默認: 無執行上下文，當前執行續運行所有操作
//        Schedulers.single(); // 使用一個固定的單執行續
//        Schedulers.boundedElastic(); // 有限的，彈性調度，不限無限擴充的執行緒池，
//        執行敘池中有 10 * CPU 執行敘池，佇列默認 100 K，keepAliveTime: 60s
        Schedulers.fromExecutor(new ThreadPoolExecutor(4, 8, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1000)));

        // 只要不指定執行緒池，默認發布者用的執行敘池就是訂閱者的執行敘池
        // 流的每個操作都會產生新流，產生新的發布者


    }

    public void create() throws InterruptedException {

        // 異步環境下
        Flux.create(fluxSink -> {
            MyListener myListener = new MyListener(fluxSink);
            for (int i = 0; i < 100; i++) {
                myListener.online("張" + i);
            }
        })
                .log()
                .subscribe();
    }

    class MyListener {
        FluxSink<Object> sink;
        public MyListener (FluxSink<Object> sink) {
            this.sink = sink;
        }
        // 用戶登入，觸發 online 監聽
        public void online(String userName) {
            System.out.println("用戶登入了：" + userName);
            sink.next(userName); // 傳入用戶
        }
    }

    public void dispose() {
        Flux<Integer> flux = Flux.range(1, 10000)
                .delayElements(Duration.ofSeconds(1))
                .map(i -> i + 7)
                .log();


        // 1. 消費者是實現了 Disposable 可取消
        Disposable disposable = flux.subscribe(System.out::println);

        new Thread(() -> {
            try {
                Thread.sleep(10000);
                disposable.dispose(); // 銷毀
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    // 編程方式創建序列
    // Sink: 接收器、水槽、通道;
    // Source: 資料源 Sink: 接受端
    public void generate() {
        Flux<Object> flux = Flux.generate(() -> 0, // 初始 state 值
                (state, sink) -> {
                    if (state <= 10) {
                        // 0- 10
                        sink.next(state);
                    } else {
                        sink.complete(); // 完成, 完成信號
                    }

                    if (state == 7) {
                        sink.error(new RuntimeException("我不喜歡 7 "));
                    }
                    return state + 1; // 返回新的迭代值 state 值
                });
//        Flux<Object> flux = Flux.generate(sink -> {
//            for (int i = 0; i < 100; i++) {
//                sink.next("哈哈-" + i); // 傳遞資料，可能會拋出[不受檢異常（運行時異常）、受檢異常（編譯時異常）]
//            }
//        });
        flux
                .log()
                .doOnError(throwable -> {
                    System.out.println("throwable = " + throwable);
                })
                .subscribe();
    }

    // 限流
    public void limit() {
         Flux.range(1, 1000)
             .log() // 限流觸發，看上游是怎麼限流獲取資料的
                .limitRate(100) // 一次預取 100 個元素，第一次 request(100)，以後 request(75)
                    .subscribe(new BaseSubscriber<Integer>() {
                        @Override
                        protected void hookOnSubscribe(Subscription subscription) {
                            request(1); // 取一次
                        }

                        @Override
                        protected void hookOnNext(Integer value) {
                            System.out.println(value);
                            request(1);
                        }
                    });

         // 75% 預取策略: limitRate(100)
         // 第一次抓取 100 個元素，如果 75% 的元素已經處理過了，繼續抓取新的 75% 元素;
    }

    public void buffer() {
        Flux<List<Integer>> buffer = Flux.range(1, 10) // 原始流 10 個
                .buffer(2)// 添加了緩衝區：緩衝了 2(n) 個元素，消費者一次最多可以拿到三個元素; 湊滿數量之後批量給消費者
                        .log();

        // 尚未使用 buffer 前，一次發一個，一個一個發;

        // 所有操作符作用於發佈者流。

        // 消費者每次 request(1) 拿到的是幾個真正的資料：buffer 的資料
//        buffer.subscribe(v -> {
//            System.out.println("類型: " + v.getClass() + " 值: " + v);
//            for (Integer integer : v) {
//                System.out.println("查詢訂單：" + integer);
//            }
//        });

        buffer.subscribe(new BaseSubscriber<List<Integer>>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                System.out.println("綁定關係...");
                request(1);
            }

            @Override
            protected void hookOnNext(List<Integer> value) {
                System.out.println("元素：" + value);
            }
        }); // 自定義訂閱者感知


    }

    /**
     * subscrible: 訂閱流: 沒訂閱之前什麼也不做
     * -> subscrible 開始，流的元素開始流動，發生資料變化
     * -> 響應式編程：資料流(Flux/Mono) + 變化傳播（流的操作）
     * @param args
     */
    public void customSubscrible(String[] args) {

        // onErrorXxx, doOnXxx 不一樣
        // doOnXxx：發生這個事件的時候產生一個回調，通知你（僅此而已，不能改變元素、信號）
        // onXxx：發生這個事件執行一個動作。這個動作可以改變元素、信號。
        // like AOP: 普通通知（前置、後置、異常、返回） v.s. 環繞通知（ProceedingJoinPoint)

        // y = kx + b
        Flux<String> flux = Flux.range(1, 10)
                .map(i ->  {
                    System.out.println("map..." + i);
                    if (i == 9) {
                        i = 10 / (9 - i); // 數學運算異常; doOnXxx
                    }
                    return "哈哈: " + i;
                })
                .onErrorComplete(); // 流錯誤的時候，把錯誤吃掉，轉為正常信號

//        flux.subscribe(); // 流被訂閱; 默認訂閱;
//        flux.subscribe(v -> System.out.println("v = " + v)); // 指定訂閱規則，只傳入正常消費者; 只消費正常元素
//        flux.subscribe(
//                v -> System.out.println("v = " + v), // 流元素消費
//                throwable -> System.out.println("throwable = " + throwable), // 感知異常結束
//                () -> System.out.println("流結束了...") // 感知正常結束
//        );

        // 流的生命週期鉤子可以傳播給訂閱者。
        /*
            a() {
                data = b();
            }
         */
        flux.subscribe(new BaseSubscriber<String>() {

            // 生命週期鉤子1: 訂閱關係綁定的時候觸發
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                // 流被訂閱的時候觸發
                System.out.println("綁定了..." + subscription);

                // 找發佈者要資料
                request(1); // 要一個資料; 給上游傳入資料傳送一個訊號
//                requestUnbounded(); // 沒有邊界的（要無限資料）; 上游給我所有的訊號
            }

            @Override // 每個元素觸發 onNext 方法
            protected void hookOnNext(String value) {
                System.out.println("資料到達，正在處理： " + value);
                if (value.equals("哈哈: 5")) {
                    cancel(); // 取消流
                }
                request(1); // 要一個資料

            }

            // hookOnComplete、hookOnError 二選一執行
            @Override
            protected void hookOnComplete() {
                System.out.println("流正常結束了...");
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                System.out.println("流異常了..." + throwable);
            }

            @Override
            protected void hookOnCancel() {
                System.out.println("流被取消了...");
            }

            @Override
            protected void hookFinally(SignalType type) {
                System.out.println("最終回調...，一定會被執行");
            }
        });
    }

    public static void fluxMono(String[] args) {
//        Flux.concat(Flux.just(1, 2, 3), Flux.just(7, 8, 9))
//                .subscribe(System.out::println);

        Flux.range(1, 7)
                .log() // 日誌
                .filter(i -> i > 3) // 挑出 > 3 的元素
                // .log() // onNext(4~7)
                .map(i -> "haha-" + i)
                // .log() // onNext(haha-4~7)
                .subscribe(System.out::println);

        // 今天： Flux、Mono：彈珠圖、事件感知 API 每個操作都是操作上個流的東西
    }

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
