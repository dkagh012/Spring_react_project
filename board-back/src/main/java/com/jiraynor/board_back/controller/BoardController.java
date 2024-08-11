package com.jiraynor.board_back.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jiraynor.board_back.DTO.request.board.PostBoardRequestDto;
import com.jiraynor.board_back.DTO.response.board.PostBoardResponseDto;
import com.jiraynor.board_back.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {
    
    // BoardService를 주입받아 게시물 관련 로직을 처리하는 컨트롤러입니다.
    private final BoardService boardService;

    // 새로운 게시물을 생성하는 엔드포인트입니다.
    @PostMapping("")
    public ResponseEntity<? super PostBoardResponseDto> postBoard(
        @RequestBody @Valid PostBoardRequestDto requestBody, // 클라이언트로부터 게시물 데이터를 받아옵니다.
        @AuthenticationPrincipal String email // 인증된 사용자의 이메일을 받아옵니다.
    ){
        // BoardService의 postBoard 메서드를 호출하여 게시물을 생성하고 응답을 반환합니다.
        ResponseEntity<? super PostBoardResponseDto> response = boardService.postBoard(requestBody, email);
        return response; // 최종 응답을 클라이언트에게 반환합니다.
    }
}
