package com.ming.navermyshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // 시간 자동 변경이 가능하도록 합니다.
@SpringBootApplication // 스프링 부트임을 선언합니다.
public class NaverMyShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(NaverMyShopApplication.class, args);
    }
}
