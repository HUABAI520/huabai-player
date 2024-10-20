package com.ithe.huabaiplayer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author L
 */
@SpringBootApplication
@MapperScan({"com.ithe.huabaiplayer.**.mapper"})
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class HuabaiPlayerApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(HuabaiPlayerApplication.class, args);
    }

}
