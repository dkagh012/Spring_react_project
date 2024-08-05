package com.jiraynor.board_back.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jiraynor.board_back.provider.JwtProvider;

import org.springframework.util.StringUtils; // 유틸리티 클래스 임포트
import lombok.RequiredArgsConstructor;

// 이 클래스를 Spring Bean으로 등록
@Component
// Lombok 애노테이션을 사용하여 final 필드에 대한 생성자를 자동으로 생성
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    // JwtProvider를 주입받음
    private final JwtProvider jwtProvider;

    // 요청을 필터링하는 메서드
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // 요청에서 JWT를 파싱
            String token = parseBearerToken(request);

            // 토큰이 없으면 다음 필터로 진행
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            // 토큰을 검증하고 이메일을 얻음
            String email = jwtProvider.validate(token);
            if (email == null) {
                filterChain.doFilter(request, response);
                return;
            }

            // 이메일을 기반으로 인증 토큰을 생성
            AbstractAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, null, AuthorityUtils.NO_AUTHORITIES);

            // 요청의 세부 정보를 설정
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 빈 SecurityContext를 생성하고 인증 정보를 설정
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authenticationToken);

            // SecurityContextHolder에 SecurityContext를 설정
            SecurityContextHolder.setContext(securityContext);
        } catch (Exception exception) {
            // 예외가 발생하면 스택 트레이스를 출력
            exception.printStackTrace();
        }

        // 다음 필터로 진행
        filterChain.doFilter(request, response);
    }

    // 요청에서 Bearer 토큰을 파싱하는 메서드
    private String parseBearerToken(HttpServletRequest request) {
        
        // 요청 헤더에서 Authorization 값을 가져옴
        String authorization = request.getHeader("Authorization");

        // Authorization 값이 있는지 확인
        boolean hasAuthorization = StringUtils.hasText(authorization);
        if (!hasAuthorization) return null;

        // Authorization 값이 Bearer로 시작하는지 확인
        boolean isBearer = authorization.startsWith("Bearer");
        if (!isBearer) return null;

        // Bearer 이후의 토큰 값을 추출하여 반환
        String token = authorization.substring(7);
        return token;
    }
}
