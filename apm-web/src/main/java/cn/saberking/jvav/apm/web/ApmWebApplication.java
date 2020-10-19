package cn.saberking.jvav.apm.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author saber-kings
 */
@MapperScan(basePackages = {"cn.saberking.jvav.apm.web.mapper"})
@SpringBootApplication
public class ApmWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApmWebApplication.class, args);
    }

}
