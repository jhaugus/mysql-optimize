package com.augus.user.test;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public class TimeTest {


    public static void main(String[] args) throws InterruptedException {
        // 记录开始时间
        Instant start = Instant.now();

        // 模拟一些处理时间
        Thread.sleep(2000);

        // 记录结束时间
        Instant end = Instant.now();

        // 计算时间间隔
        Duration duration = Duration.between(start, end);
        System.out.println("处理时间: " + duration.toMillis() + " 毫秒");
    }
}
