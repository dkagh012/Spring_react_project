package com.jiraynor.board_back.DTO.response.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jiraynor.board_back.DTO.response.ResponseDto;
import com.jiraynor.board_back.common.ResponseCode;
import com.jiraynor.board_back.common.ResponseMessage;
import com.jiraynor.board_back.entity.UserEntity;

import lombok.Getter;

@Getter // 이 클래스의 필드들에 대해 getter 메서드를 자동으로 생성해줍니다.
public class GetSignInUserResponseDto extends ResponseDto {
    
    private String email; // 사용자의 이메일
    private String nickname; // 사용자의 닉네임
    private String profileImage; // 사용자의 프로필 이미지 URL

    // private 생성자: 외부에서 직접 객체를 생성하지 않고, 정적 팩토리 메서드를 통해 생성할 수 있게 합니다.
    private GetSignInUserResponseDto(UserEntity userEntity){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS); // 부모 클래스의 생성자를 호출하여 성공 응답 코드를 설정합니다.
        this.email = userEntity.getEmail(); // UserEntity 객체에서 이메일을 가져와 설정합니다.
        this.nickname = userEntity.getNickname(); // UserEntity 객체에서 닉네임을 가져와 설정합니다.
        this.profileImage = userEntity.getProfileImage(); // UserEntity 객체에서 프로필 이미지를 가져와 설정합니다.
    }

    // 성공 응답을 반환하는 정적 팩토리 메서드
    public static ResponseEntity<GetSignInUserResponseDto> success(UserEntity userEntity){
        GetSignInUserResponseDto result = new GetSignInUserResponseDto(userEntity); // 주어진 UserEntity로 응답 객체를 생성합니다.
        return ResponseEntity.status(HttpStatus.OK).body(result); // HTTP 200 OK 상태와 함께 응답을 반환합니다.
    }

    // 사용자가 존재하지 않을 때의 응답을 반환하는 정적 팩토리 메서드
    public static ResponseEntity<ResponseDto> notExistUser(){
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER); // 사용자 없음 응답 객체 생성
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result); // HTTP 401 Unauthorized 상태와 함께 응답을 반환합니다.
    }

}
