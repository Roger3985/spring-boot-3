package com.atguigu;

/**
 * Hello world!
 *
 */

/**
 * 打包成本地鏡像
 * 1. 打成 jar 包：注意修改 jar 包內的 MANIFEST.MF 文件，指定 Main-Class 的全類名
 *    - java -jar xxx.jar 就可以執行。
 *    - 切換機器，需要安裝 java 環境。默認解釋執行，啟動速度慢，運行速度慢
 * 2. 打包成本地鏡像（可執行文件）
 *    - native-image -cp 你的 jar 包/路徑 你的主類 -o 輸出的文件名
 *    - native-image -cp boot3-15-aot-common-1.0-SNAPSHOT.jar com.atguigu.MainApplication -o Demo
 * 並不是所有的 Java 代碼都能支持本地打包; SpringBoot 保證 Spring 應用的所有程序都能在 AOT 的時候提前告知 graalvm 怎麼處理？
 *   - 動態能力損失：反射的代碼：（動態獲取建構器，反射創建，反射調用一些方法）;
 *     解決方案：額外處理（SpringBoot 提供了一些註解）; 提前告知 graalvm 反射會用到哪些方法、建構器
 *   - 配置文件損失：
 *     解決方案：額外處理（配置中心）：提前告知 graalvm 配置文件怎麼處理
 *     二進制裡面不能包含的，不能動態的都得提前處理 ;
 *     不是所有的框架都適用 AOT 特性; Spring 全系列棧都適配 OK
 */
public class MainApplication {
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
