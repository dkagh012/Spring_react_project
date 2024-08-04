package com.jiraynor.board_back.DTO.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jiraynor.board_back.common.ResponseCode;
import com.jiraynor.board_back.common.ResponseMessage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto {

    private String code; // 응답 코드
    private String message; // 응답 메시지

    // 데이터베이스 오류 발생 시 사용할 정적 메서드
    public static ResponseEntity<ResponseDto> databaseError() {
        // ResponseDto 객체를 생성하여 데이터베이스 오류 코드와 메시지를 설정
        ResponseDto responseBody = new ResponseDto(ResponseCode.DATABASE_ERROR, ResponseMessage.DATABASE_ERROR);
        // HTTP 상태 코드 500(INTERNAL_SERVER_ERROR)와 함께 응답 본문에 responseBody 객체를 담아 반환
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }
}
