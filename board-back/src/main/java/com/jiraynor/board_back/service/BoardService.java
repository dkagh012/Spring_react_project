package com.jiraynor.board_back.service;

import org.springframework.http.ResponseEntity;

import com.jiraynor.board_back.DTO.request.board.PostBoardRequestDto;
import com.jiraynor.board_back.DTO.response.board.PostBoardResponseDto;

// 게시물 관련 기능을 정의하는 인터페이스입니다.
public interface BoardService {
    
    // 게시물을 생성하는 메서드입니다.
    // 게시물 생성 요청 데이터와 사용자의 이메일을 매개변수로 받아 처리합니다.
    // 게시물 생성 결과를 포함하는 ResponseEntity를 반환합니다.
    ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email);
}
