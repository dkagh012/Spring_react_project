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

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService; // AuthService 인스턴스를 주입받습니다.

    // 회원가입 요청을 처리하는 엔드포인트입니다.
    @PostMapping("/sign-up")
    public ResponseEntity<? super SignUpResponseDto> signUp(
        @RequestBody @Valid SignUpRequestDto requestBody // 요청 본문에서 SignUpRequestDto 객체를 유효성 검사와 함께 받습니다.
    ) {
        // AuthService의 signUp 메서드를 호출하여 응답을 처리합니다.
        ResponseEntity <? super SignUpResponseDto> response = authService.signUp(requestBody);
        return response; // 처리된 응답을 클라이언트에 반환합니다.
    }

    // 로그인 요청을 처리하는 엔드포인트입니다.
    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDto> signIn(
        @RequestBody @Valid SignInRequestDto requestBody // 요청 본문에서 SignInRequestDto 객체를 유효성 검사와 함께 받습니다.
    )   {
        // AuthService의 signIn 메서드를 호출하여 응답을 처리합니다.
        ResponseEntity<? super SignInResponseDto> response = authService.signIn(requestBody);
        return response; // 처리된 응답을 클라이언트에 반환합니다.
    }
}
