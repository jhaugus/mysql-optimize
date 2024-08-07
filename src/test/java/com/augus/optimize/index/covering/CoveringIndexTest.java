package com.augus.optimize.index.covering;

import com.augus.user.entity.UserEntity;
import com.augus.user.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@SpringBootTest
public class CoveringIndexTest {

    @Autowired
    private UserService userService;


    @Test
    public void testCoveringIndex() {
        // 记录开始时间
        Instant start = Instant.now();

        userService.getById(1);

        // 记录结束时间
        Instant end = Instant.now();

        // 计算时间间隔
        Duration duration = Duration.between(start, end);
        System.out.println("处理时间: " + duration.toMillis() + " 毫秒");

    }



    @Test
    public void testCoveringIndex2() {
        // 记录开始时间
        Instant start = Instant.now();
        for(int i = 1; i < 1000; i++){
            List<Long> activeUserIds = userService.getActiveUserIds();
        }
        // 记录结束时间
        Instant end = Instant.now();

        // 计算时间间隔
        Duration duration = Duration.between(start, end);
        System.out.println("处理时间: " + duration.toMillis() + " 毫秒");

    }


    @Test
    public void testCoveringIndex3() {
        // 记录开始时间
        Instant start = Instant.now();
        for(int i = 1; i < 1000; i++){
            List<Long> activeUserIds = userService.getAllUserIds();
        }
        // 记录结束时间
        Instant end = Instant.now();

        // 计算时间间隔
        Duration duration = Duration.between(start, end);
        System.out.println("处理时间: " + duration.toMillis() + " 毫秒");

    }

}
