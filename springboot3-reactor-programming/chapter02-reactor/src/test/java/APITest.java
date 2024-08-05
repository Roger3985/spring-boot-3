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
