package com.jiraynor.board_back.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jiraynor.board_back.DTO.response.user.GetUserResponseDto;
import com.jiraynor.board_back.DTO.response.user.PatchNicknameResponseDto;
import com.jiraynor.board_back.DTO.response.user.PatchProfileImageResponseDto;
import com.jiraynor.board_back.DTO.request.user.PatchNicknameRequestDto;
import com.jiraynor.board_back.DTO.request.user.PatchProfileImageRequestDto;
import com.jiraynor.board_back.DTO.response.user.GetSignInUserResponseDto;
import com.jiraynor.board_back.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController // 이 클래스가 RESTful 웹 서비스의 컨트롤러임을 나타냅니다.
@RequestMapping("/api/v1/user") // 이 컨트롤러의 기본 요청 경로를 설정합니다.
@RequiredArgsConstructor // final 필드에 대해 자동으로 생성자를 생성해줍니다.
public class UserController {
        // UserService를 의존성 주입을 통해 사용합니다.
        private final UserService userService;

        @GetMapping("/{email}")
        public ResponseEntity<? super GetUserResponseDto> getUser(
            @PathVariable("email") String email
        ){
        ResponseEntity<? super GetUserResponseDto> response = userService.getUser(email);
        return response;
        }


        @GetMapping("") // HTTP GET 요청을 처리할 메서드를 정의합니다. 여기서는 "/api/v1/user"로 들어오는 GET 요청을 처리합니다.
        public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(
            @AuthenticationPrincipal String email // Spring Security를 통해 인증된 사용자의 이메일을 매개변수로 받습니다.
        ){
            // UserService를 호출하여 이메일에 해당하는 사용자의 정보를 가져옵니다.
            ResponseEntity<? super GetSignInUserResponseDto> response = userService.getSignInUser(email);
            // 서비스에서 반환된 응답을 그대로 반환합니다.
            return response;
        }

        @PatchMapping("/nickname")
        public ResponseEntity<? super PatchNicknameResponseDto> patchNickname(
            @RequestBody @Valid PatchNicknameRequestDto requestBody,
            @AuthenticationPrincipal String email
        ) {
            ResponseEntity<? super PatchNicknameResponseDto> response = userService.patchNickname(requestBody, email);
            return response;
        }

        @PatchMapping("/profile-image")
        public ResponseEntity<? super PatchProfileImageResponseDto> patchProfileImage(
            @RequestBody @Valid PatchProfileImageRequestDto requestBody,
            @AuthenticationPrincipal String email
        ) {
            ResponseEntity<? super PatchProfileImageResponseDto> response = userService.patchProfileImage(requestBody, email);
            return response;
        }

}
