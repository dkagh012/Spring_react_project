package com.jiraynor.board_back.service;

import org.springframework.http.ResponseEntity;
import com.jiraynor.board_back.DTO.request.auth.SignUpRequestDto;
import com.jiraynor.board_back.DTO.response.auth.SignUpResponseDto;

public interface AuthService {
    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto); // 여기서 메서드 이름과 서명 확인
}
