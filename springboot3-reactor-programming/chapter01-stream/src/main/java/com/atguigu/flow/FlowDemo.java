package com.atguigu.flow;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * @author Roger
 * @Description
 * @create 2024-08-04
 */
public class FlowDemo {

    // 定義流中間操作處理器; 只用寫訂閱者介面
    static class MyProcessor extends SubmissionPublisher<String> implements Flow.Processor<String, String> {

        private Flow.Subscription subscription; // 保存綁定關係

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            System.out.println("processor 訂閱綁定完成");
            this.subscription = subscription;
            subscription.request(1); // 找上游要一個資料
        }

        @Override // 資料到達，觸發這個回調
        public void onNext(String item) {
            System.out.println("process 拿到資料：" + item);
            // 再加工
            item += ":哈哈";
            submit(item); // 把我加工後的資料發出去
            subscription.request(1); // 再要新資料
        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void onComplete() {

        }
    }

    /**
     * 1. Publisher: 發佈者
     * 2. Subscriber: 訂閱者
     * 3. Subscription: 訂閱關係
     * 4. Processor: 處理器
     * @param args
     */

    // 發佈訂閱模型：觀察者模式，
    public static void main(String[] args) throws InterruptedException {
        // 1. 定義一個發佈者，發佈資料
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();


//        Flow.Publisher<String> publisher = new Flow.Publisher<String>() {
//            private Flow.Subscriber<? super String> subscriber;
//            // 訂閱者會訂閱這個發佈者的介面
//            @Override
//            public void subscribe(Flow.Subscriber<? super String> subscriber) {
//                this.subscriber = subscriber;
//            }
//        };

        // 2. 定義一個中間操作：給每個元素加個 哈哈 前綴
        MyProcessor myProcessor1 = new MyProcessor();
        MyProcessor myProcessor2 = new MyProcessor();
        MyProcessor myProcessor3 = new MyProcessor();

        // 3. 定義一個訂閱者，訂閱者感興發佈者的資料
        Flow.Processor<String, String> subscriber = new Flow.Processor<String, String>() {
            @Override
            public void subscribe(Flow.Subscriber<? super String> subscriber) {

            }

            @Override
            public void onSubscribe(Flow.Subscription subscription) {

            }

            @Override
            public void onNext(String item) {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        };

        // 3. 定義一個訂閱者，訂閱者感興發佈者的資料
        Flow.Subscriber<String> subscriber1 = new Flow.Subscriber<String>() {
            private Flow.Subscription subscription;

            @Override // 在訂閱時， onXxxx：在 xxx 事件發生時，執行這個回調
            public void onSubscribe(Flow.Subscription subscription) {
                System.out.println(Thread.currentThread() + "訂閱開始了：" + subscription);
                this.subscription = subscription;

                // 從上游請求一個資料
                subscription.request(1);
            }

            @Override // 在下一個元素到達時：執行這個回調，接受到新資料
            public void onNext(String item) {
                System.out.println(Thread.currentThread() + "訂閱者，接受到新資料：" + item);

                if (item.equals("p-7")) {
                    subscription.cancel(); // 取消訂閱
                } else {
                    subscription.request(1);
                }
            }

            @Override // 在錯誤發生時
            public void onError(Throwable throwable) {
                System.out.println(Thread.currentThread() + "訂閱者，接受到錯誤信號：" + throwable);
            }

            @Override // 在完成時
            public void onComplete() {
                System.out.println(Thread.currentThread() + "訂閱者，接受到完成資料信號");
            }
        };

        Flow.Subscriber<String> subscriber2 = new Flow.Subscriber<String>() {
            private Flow.Subscriber subscriber;

            @Override // 在訂閱時， onXxxx：在 xxx 事件發生時，執行這個回調
            public void onSubscribe(Flow.Subscription subscription) {
                System.out.println(Thread.currentThread() + "訂閱開始了：" + subscription);
                this.subscriber = subscriber;
            }

            @Override // 在下一個元素到達時：執行這個回調，接受到新資料
            public void onNext(String item) {
                System.out.println(Thread.currentThread() + "訂閱者，接受到新資料：" + item);
            }

            @Override // 在錯誤發生時
            public void onError(Throwable throwable) {
                System.out.println(Thread.currentThread() + "訂閱者，接受到錯誤信號：" + throwable);
            }

            @Override // 在完成時
            public void onComplete() {
                System.out.println(Thread.currentThread() + "訂閱者，接受到完成資料信號");
            }
        };

        // 4. 綁定發佈者和訂閱者
//        publisher.subscribe(subscriber1);
        publisher.subscribe(myProcessor1); // 此時處理器相當於訂閱者
        myProcessor1.subscribe(myProcessor2); // 此時處理器相當於發佈者
        myProcessor2.subscribe(myProcessor3);
        myProcessor3.subscribe(subscriber1); // 鏈表關係綁定出責任鏈
        // 以上綁定操作; 就是發佈者，記住了所有訂閱者都有誰，有資料後，給所有訂閱者把資料推送出去。

        for (int i = 0; i < 10; i++) {
            // 發佈者發佈十條資料
            System.out.println(Thread.currentThread());
            publisher.submit("p-" + i);
//            if (i >= 9) {
                // 中斷
//                publisher.closeExceptionally(new RuntimeException("數字大於 9，不想幹了！"));
//            } else {
//                publisher.submit("p-" + i);
//                // publisher 發佈的所有資料在它的 buffer 區。
//            }
        }

        // ReactiveStream

        // jvm 底層對於整個發佈訂閱關係做好了 異步 + 緩衝區處理 = 響應式系統

        // 發佈者通道關閉
        publisher.close();


//        publisher.subscribe(subscriber2);

        // 發佈者有資料，訂閱者就會拿到

        Thread.sleep(20000);
    }
}
