# 使用 Amazon Corretto 21 映像檔作為基礎映像
FROM amazoncorretto:21

# 設置應用程式的工作目錄
WORKDIR /app

# 複製生成的 .jar 檔案到容器中
COPY target/boot3-14-actuator-0.0.1-SNAPSHOT.jar app.jar

# 應用程式運行時的端口
EXPOSE 9999

# 運行時環境變數
# ENV SPRING_PROFILES_ACTIVE=dev

# 設定容器啟動時執行的命令
ENTRYPOINT ["java","-jar", "app.jar"]