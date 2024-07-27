package com.jiraynor.board_back.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jiraynor.board_back.provider.JwtProvider;

import org.springframework.util.StringUtils; // 변경된 임포트
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doFilterInternal'");
    }

    private String parseBearerToken(HttpServletRequest request){
        
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
