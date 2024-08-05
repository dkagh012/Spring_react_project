package com.jiraynor.board_back.DTO.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jiraynor.board_back.common.ResponseCode;
import com.jiraynor.board_back.common.ResponseMessage;

import lombok.AllArgsConstructor;
import lombok.Getter;

// Lombok 애노테이션을 사용하여 getter 메서드를 자동으로 생성합니다.
@Getter
// Lombok 애노테이션을 사용하여 모든 필드를 매개변수로 받는 생성자를 자동으로 생성합니다.
@AllArgsConstructor
public class ResponseDto {

    // 응답 코드 (예: "200", "500" 등)
    private String code;
    // 응답 메시지 (예: "성공", "데이터베이스 오류" 등)
    private String message;

    // 데이터베이스 오류가 발생했을 때 응답을 생성하는 정적 메서드
    public static ResponseEntity<ResponseDto> databaseError() {
        // ResponseDto 객체를 생성하여 데이터베이스 오류 코드와 메시지를 설정
        ResponseDto responseBody = new ResponseDto(
            ResponseCode.DATABASE_ERROR,  // 오류 코드
            ResponseMessage.DATABASE_ERROR // 오류 메시지
        );

        // HTTP 상태 코드 500(INTERNAL_SERVER_ERROR)와 함께 응답 본문에 responseBody 객체를 담아 반환
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }
}
