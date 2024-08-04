package com.jiraynor.board_back.DTO.response.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jiraynor.board_back.DTO.response.ResponseDto;
import com.jiraynor.board_back.common.ResponseCode;
import com.jiraynor.board_back.common.ResponseMessage;

import lombok.Getter;

@Getter
public class SignUpResponseDto extends ResponseDto {
    
    // 기본 생성자를 private으로 설정하여 외부에서 인스턴스 생성을 막음
    private SignUpResponseDto(){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS); // 성공 응답 코드와 메시지로 초기화
    }

    // 회원가입 성공 응답을 반환하는 정적 메서드
    public static ResponseEntity<SignUpResponseDto> success(){
        SignUpResponseDto result = new SignUpResponseDto(); // 새로운 SignUpResponseDto 인스턴스 생성
        return ResponseEntity.status(HttpStatus.OK).body(result); // HTTP 상태 코드 200(OK)와 함께 응답 반환
    }

    // 이메일 중복 응답을 반환하는 정적 메서드
    public static ResponseEntity<ResponseDto> duplicateEmail(){
        ResponseDto result = new ResponseDto(ResponseCode.DUPLICATE_EMAIL, ResponseMessage.DUPLICATE_EMAIL); // 중복된 이메일 응답 생성
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result); // HTTP 상태 코드 400(Bad Request)와 함께 응답 반환
    }

    // 닉네임 중복 응답을 반환하는 정적 메서드
    public static ResponseEntity<ResponseDto> duplicateNickname(){
        ResponseDto result = new ResponseDto(ResponseCode.DUPLICATE_NICKNAME, ResponseMessage.DUPLICATE_NICKNAME); // 중복된 닉네임 응답 생성
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result); // HTTP 상태 코드 400(Bad Request)와 함께 응답 반환
    }

    // 전화번호 중복 응답을 반환하는 정적 메서드
    public static ResponseEntity<ResponseDto> duplicateTelNumber(){
        ResponseDto result = new ResponseDto(ResponseCode.DUPLICATE_TEL_NUMBER, ResponseMessage.DUPLICATE_TEL_NUMBER); // 중복된 전화번호 응답 생성
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result); // HTTP 상태 코드 400(Bad Request)와 함께 응답 반환
    }
}
