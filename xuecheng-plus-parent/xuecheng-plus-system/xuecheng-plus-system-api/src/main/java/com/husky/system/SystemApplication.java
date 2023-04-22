package com.husky.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/8
 * Time: 15:39
 * Description: 描述该类的功能
 */
@SpringBootApplication
@MapperScan(basePackages = "com.husky.system.mapper")
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}
