package com.hmdp;

import com.hmdp.service.IShopService;
import com.hmdp.service.impl.ShopServiceImpl;
import com.hmdp.utils.RedisIdWorker;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class HmDianPingApplicationTests {

    @Resource
    private ShopServiceImpl shopService;
    private ExecutorService executorService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisIdWorker redisIdWorker;
    @Test
    void testSaveShop2Redis(){
        ShopServiceImpl date = shopService;
        for (long i = 1L; i < 15L; i++) {
            date.saveShop2Redis(i,10L);

        }
    }

    @Test
    void testRedisIdWorker() throws InterruptedException {
        executorService = Executors.newFixedThreadPool(500);
        long l = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            Runnable task = () -> {
                for (int j = 0; j < 100; j++) {
                    System.out.println("id = " +redisIdWorker.nextId("shop:id"));
                }
            };
            executorService.submit(task);
        }
        executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);
        System.out.println("耗时：" + (System.currentTimeMillis() - l));
    }
}
