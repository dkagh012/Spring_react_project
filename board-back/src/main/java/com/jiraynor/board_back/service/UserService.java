package com.jiraynor.board_back.service;

import org.springframework.http.ResponseEntity;

import com.jiraynor.board_back.DTO.request.user.PatchNicknameRequestDto;
import com.jiraynor.board_back.DTO.request.user.PatchProfileImageRequestDto;
import com.jiraynor.board_back.DTO.response.user.GetSignInUserResponseDto;
import com.jiraynor.board_back.DTO.response.user.GetUserResponseDto;
import com.jiraynor.board_back.DTO.response.user.PatchNicknameResponseDto;
import com.jiraynor.board_back.DTO.response.user.PatchProfileImageResponseDto;

public interface UserService {

    ResponseEntity<? super GetUserResponseDto> getUser(String email);
    // 이메일을 통해 사용자 정보를 조회하고, 그 결과를 ResponseEntity에 담아 반환하는 메서드 정의
    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email);    

    ResponseEntity<? super PatchNicknameResponseDto> patchNickname(PatchNicknameRequestDto dto , String email);
    ResponseEntity<? super PatchProfileImageResponseDto> patchProfileImage(PatchProfileImageRequestDto dto,String email);
    
}
