package com.jiraynor.board_back.DTO.response.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jiraynor.board_back.DTO.response.ResponseDto;
import com.jiraynor.board_back.common.ResponseCode;
import com.jiraynor.board_back.common.ResponseMessage;

import lombok.Getter;

@Getter
public class SignInResponseDto extends ResponseDto {
    
    private String token; // JWT 토큰
    private int expirationTime; // 토큰의 만료 시간 (초 단위로 3600초 = 1시간)

    // private 생성자, 성공 시 메시지와 토큰을 설정
    private SignInResponseDto(String token){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS); // 상위 클래스인 ResponseDto의 생성자를 호출하여 성공 메시지와 코드를 설정
        this.token = token; // 토큰 설정
        this.expirationTime = 3600; // 만료 시간을 3600초로 설정
    }

    // 성공 시 응답을 생성하는 static 메서드
    public static ResponseEntity<SignInResponseDto> success(String token){
        SignInResponseDto result = new SignInResponseDto(token); // 토큰을 이용해 SignInResponseDto 객체 생성
        return ResponseEntity.status(HttpStatus.OK).body(result); // HTTP 상태 코드 200(OK)와 함께 응답 본문에 result 객체를 담아 반환
    }

    // 로그인 실패 시 응답을 생성하는 static 메서드
    public static ResponseEntity<ResponseDto> signInFail(){
        ResponseDto result = new ResponseDto(ResponseCode.SIGN_IN_FAIL, ResponseMessage.SIGN_IN_FAIL); // 실패 코드와 메시지를 담은 ResponseDto 객체 생성
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result); // HTTP 상태 코드 401(Unauthorized)와 함께 응답 본문에 result 객체를 담아 반환
    }
}
