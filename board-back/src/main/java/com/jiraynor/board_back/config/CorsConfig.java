package com.jiraynor.board_back.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 스프링 설정 클래스를 나타내는 어노테이션
@Configuration
// CORS 설정을 위한 WebMvcConfigurer 구현
public class CorsConfig implements WebMvcConfigurer {

    // CORS 매핑을 추가하는 메서드 오버라이드
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

        // 모든 경로에 대해 CORS 설정을 추가
        corsRegistry
            .addMapping("/**") // 모든 경로에 대해 CORS 설정 적용
            .allowedMethods("*") // 모든 HTTP 메서드 허용 (GET, POST, PUT, DELETE 등)
            .allowedOrigins("*"); // 모든 도메인에서의 요청 허용
    }
}
