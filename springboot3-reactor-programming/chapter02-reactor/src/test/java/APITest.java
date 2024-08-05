import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @author 2407009
 * @Description
 * @create 2024-08-05
 */
public class APITest {

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
     *  - concat: Flux.concat
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
