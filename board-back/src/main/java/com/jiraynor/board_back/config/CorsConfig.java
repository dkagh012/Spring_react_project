package com.jiraynor.board_back.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration 애노테이션을 사용하여 이 클래스가 스프링 설정 클래스임을 나타냅니다.
@Configuration
// WebMvcConfigurer 인터페이스를 구현하여 CORS 설정을 추가합니다.
public class CorsConfig implements WebMvcConfigurer {

    // addCorsMappings 메서드를 오버라이드하여 CORS 매핑을 추가합니다.
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

        // 모든 경로에 대해 CORS 설정을 추가합니다.
        corsRegistry
            .addMapping("/**") // 모든 경로에 대해 CORS 설정을 적용합니다.
            .allowedMethods("*") // 모든 HTTP 메서드를 허용합니다. (GET, POST, PUT, DELETE 등)
            .allowedOrigins("*"); // 모든 도메인에서의 요청을 허용합니다.
    }
}
