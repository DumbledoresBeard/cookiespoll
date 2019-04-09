package net.cookiespoll;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("net.cookiespoll.mapper")
public class CookiesPoll {
    public static void main(String[] args) {
        SpringApplication.run(CookiesPoll.class, args);
    }
}
