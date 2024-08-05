package com.jiraynor.board_back.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jiraynor.board_back.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

// 스프링 설정 클래스를 나타내는 애노테이션
@Configuration
// Spring Security 설정을 활성화하는 애노테이션
@EnableWebSecurity
// 생성자를 통한 의존성 주입을 자동으로 생성해주는 Lombok 애노테이션
@RequiredArgsConstructor
public class WebSecurityConfig {

    // JwtAuthenticationFilter 의존성 주입
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // SecurityFilterChain 빈 설정
    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .cors().and() // CORS 설정
            .csrf().disable() // CSRF 보호 비활성화
            .httpBasic().disable() // HTTP 기본 인증 비활성화
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() // 세션을 상태 없이 관리
            .authorizeRequests()
            // 특정 경로에 대한 접근 권한 설정
            .antMatchers("/", "/api/v1/auth/**", "/api/v1/search/**", "/file/**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/v1/board/**", "/api/v1/user/*").permitAll()
            // 나머지 요청은 인증 필요
            .anyRequest().authenticated().and()
            // 인증 실패 시 처리할 엔트리 포인트 설정
            .exceptionHandling().authenticationEntryPoint(new FailedAuthenticationEntryPoint());

        // JWT 인증 필터를 UsernamePasswordAuthenticationFilter 앞에 추가
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // SecurityFilterChain 빌드 및 반환
        return httpSecurity.build();
    }
}

// 인증 실패 시 처리할 엔트리 포인트 클래스
class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // 인증 실패 시 호출되는 메서드
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        // 응답 콘텐츠 타입을 JSON으로 설정
        response.setContentType("application/json");
        // HTTP 상태 코드를 401 (Unauthorized)으로 설정
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // 응답 바디에 JSON 메시지 작성
        response.getWriter().write("{\"code\": \"AF\", \"message\": \"Authorization Failed.\"}");
    }
}
