package cn.saberking.jvav.apm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author apm
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ApmApplication {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(ApmApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  动漫周边商城启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                "   _____    __________     _____           \n" +
                "  /  _  \\   \\______   \\   /     \\      \n" +
                " /  /_\\  \\   |     ___/  /  \\ /  \\     \n" +
                "/    |    \\  |    |     /    Y    \\      \n" +
                "\\____|__  /  |____|     \\____|__  /      \n" +
                "        \\/                      \\/       \n");
    }
}
