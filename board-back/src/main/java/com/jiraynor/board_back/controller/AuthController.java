package com.jiraynor.board_back.controller;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jiraynor.board_back.DTO.request.auth.SignUpRequestDto;
import com.jiraynor.board_back.DTO.response.auth.SignUpResponseDto;
import com.jiraynor.board_back.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<? super SignUpResponseDto> signUp(
        @RequestBody @Valid SignUpRequestDto requestBody
    ) {
        ResponseEntity <? super SignUpResponseDto> response = authService.signUp(requestBody);
        return response;
    }
}
