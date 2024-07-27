package com.jiraynor.board_back.provider;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {
    
    // 비밀키 정의
    private String secretKey = "S3cr3tK3y";

    // JWT 생성 메소드
    public String create(String email){

        // 만료 시간 설정 (현재 시간으로부터 1시간 후)
        Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));

        // JWT 빌드 및 생성
        String jwt = Jwts.builder()
            // ES256 알고리즘과 비밀키를 사용하여 서명
            .signWith(SignatureAlgorithm.ES256, secretKey)
            // JWT 주체 설정 (이메일)
            .setSubject(email)
            // JWT 발행 시간 설정
            .setIssuedAt(new Date())
            // JWT 만료 시간 설정
            .setExpiration(expiredDate)
            // JWT 문자열로 압축
            .compact();
        
        // 생성된 JWT 반환
        return jwt;
    }

    // JWT 유효성 검증 메소드
    public String validate(String jwt){

        // 클레임을 저장할 변수
        Claims claims = null;
        
        try{
            // JWT 파싱 및 검증, 클레임 추출
            claims = Jwts.parser()
                // 서명 검증을 위한 비밀키 설정
                .setSigningKey(secretKey)
                // JWT 파싱 및 검증
                .parseClaimsJws(jwt)
                // 클레임 본문 추출
                .getBody();
        } catch (Exception exception) {
            // 예외 발생 시 스택 트레이스 출력
            exception.printStackTrace();
            // null 반환
            return null;
        }
        
        // 클레임에서 주체 (이메일) 반환
        return claims.getSubject();
    }
}
