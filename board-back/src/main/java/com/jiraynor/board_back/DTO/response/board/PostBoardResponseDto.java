package com.jiraynor.board_back.DTO.response.board;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jiraynor.board_back.DTO.response.ResponseDto;
import com.jiraynor.board_back.common.ResponseCode;
import com.jiraynor.board_back.common.ResponseMessage;

import lombok.Getter;

@Getter
public class PostBoardResponseDto extends ResponseDto{
    
    // 기본 생성자를 private으로 설정하여 외부에서 직접 인스턴스를 생성하지 못하도록 합니다.
    // 이는 이 클래스의 인스턴스가 성공 응답에서만 사용되도록 제한하기 위함입니다.
    private PostBoardResponseDto(){
        // 부모 클래스인 ResponseDto의 생성자를 호출하여 성공 상태와 메시지를 설정합니다.
        super(ResponseCode.SUCCESS , ResponseMessage.SUCCESS);
    }

    // 성공적인 응답을 생성하여 반환하는 static 메소드입니다.
    // HttpStatus.OK(200) 상태와 함께 PostBoardResponseDto 객체를 반환합니다.
    public static ResponseEntity<PostBoardResponseDto> success(){
        PostBoardResponseDto result = new PostBoardResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 존재하지 않는 엔티티에 대한 응답을 생성하여 반환하는 static 메소드입니다.
    // HttpStatus.UNAUTHORIZED(401) 상태와 함께 ResponseDto 객체를 반환합니다.
    // 이 메소드는 사용자가 존재하지 않을 때 호출됩니다.
    public static ResponseEntity<ResponseDto> notExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER,ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
}
