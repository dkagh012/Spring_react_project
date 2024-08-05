package com.jiraynor.board_back.controller;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jiraynor.board_back.DTO.request.auth.SignInRequestDto;
import com.jiraynor.board_back.DTO.response.auth.SignInResponseDto;
import com.jiraynor.board_back.DTO.request.auth.SignUpRequestDto;
import com.jiraynor.board_back.DTO.response.auth.SignUpResponseDto;
import com.jiraynor.board_back.service.AuthService;

import lombok.RequiredArgsConstructor;

// @RestController 애노테이션은 이 클래스가 RESTful 웹 서비스의 컨트롤러임을 나타냅니다.
@RestController
// @RequestMapping 애노테이션은 이 컨트롤러의 모든 엔드포인트에 대한 기본 경로를 설정합니다.
@RequestMapping("/api/v1/auth")
// @RequiredArgsConstructor 애노테이션은 Lombok을 사용하여 final 필드에 대한 생성자를 자동으로 생성합니다.
@RequiredArgsConstructor
public class AuthController {

    // AuthService 인스턴스를 주입받습니다.
    private final AuthService authService;

    // 회원가입 요청을 처리하는 엔드포인트입니다.
    @PostMapping("/sign-up")
    public ResponseEntity<? super SignUpResponseDto> signUp(
        // @RequestBody 애노테이션은 요청 본문에서 데이터를 가져오고,
        // @Valid 애노테이션은 유효성 검사를 수행합니다.
        @RequestBody @Valid SignUpRequestDto requestBody
    ) {
        // AuthService의 signUp 메서드를 호출하여 회원가입 요청을 처리하고 응답을 생성합니다.
        ResponseEntity <? super SignUpResponseDto> response = authService.signUp(requestBody);
        // 처리된 응답을 클라이언트에 반환합니다.
        return response;
    }

    // 로그인 요청을 처리하는 엔드포인트입니다.
    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDto> signIn(
        // @RequestBody 애노테이션은 요청 본문에서 데이터를 가져오고,
        // @Valid 애노테이션은 유효성 검사를 수행합니다.
        @RequestBody @Valid SignInRequestDto requestBody
    ) {
        // AuthService의 signIn 메서드를 호출하여 로그인 요청을 처리하고 응답을 생성합니다.
        ResponseEntity<? super SignInResponseDto> response = authService.signIn(requestBody);
        // 처리된 응답을 클라이언트에 반환합니다.
        return response;
    }
}
