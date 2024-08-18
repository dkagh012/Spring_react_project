package com.jiraynor.board_back.service;

import org.springframework.http.ResponseEntity;

import com.jiraynor.board_back.DTO.request.board.PostBoardRequestDto;
import com.jiraynor.board_back.DTO.request.board.PostCommentRequestDto;
import com.jiraynor.board_back.DTO.response.board.DeleteBoardResponseDto;
import com.jiraynor.board_back.DTO.response.board.GetBoardResponseDto;
import com.jiraynor.board_back.DTO.response.board.GetFavoriteListResponseDto;
import com.jiraynor.board_back.DTO.response.board.GetCommentListResponseDto;
import com.jiraynor.board_back.DTO.response.board.PutFavoriteResponseDto;
import com.jiraynor.board_back.DTO.response.board.IncreaseViewCountResponseDto;
import com.jiraynor.board_back.DTO.response.board.PostCommentResponseDto;
import com.jiraynor.board_back.DTO.response.board.PostBoardResponseDto;

// 게시물 관련 기능을 정의하는 인터페이스입니다.
public interface BoardService {
    

    ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber);
    ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer boardNumber);
    ResponseEntity<? super GetCommentListResponseDto> getCommentList(Integer boardNumber);
    // 게시물을 생성하는 메서드입니다.
    // 게시물 생성 요청 데이터와 사용자의 이메일을 매개변수로 받아 처리합니다.
    // 게시물 생성 결과를 포함하는 ResponseEntity를 반환합니다.
    ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email);

    ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto,Integer boardNumber ,String email);

    ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer boardNumber , String email);

    ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(Integer boardNumber);

    ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(Integer boardNumber , String email);
}
