package cn.saberking.jvav.apm.apmweb;

import cn.saberking.jvav.apm.web.ApmWebApplication;
import cn.saberking.jvav.apm.web.redis.RedisClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = {ApmWebApplication.class})
class ApmWebApplicationTests {
    @Resource
    private RedisClient redisClient;

    @Test
    void contextLoads() {
//        redisClient.set("you", "dog");
        String you = redisClient.get("you");
        System.out.println(you);
    }
}
