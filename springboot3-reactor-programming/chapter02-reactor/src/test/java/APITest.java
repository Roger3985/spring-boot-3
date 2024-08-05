import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.stream.Stream;

/**
 * @author 2407009
 * @Description
 * @create 2024-08-05
 */
public class APITest {

    /**
     * onSubscribe: 流被訂閱
     * request(unbounded): 請求無限資料
     * onNext(2): 每個資料到達
     * onNext(4): 每個資料到達
     * onComplete: 流結束
     */
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
