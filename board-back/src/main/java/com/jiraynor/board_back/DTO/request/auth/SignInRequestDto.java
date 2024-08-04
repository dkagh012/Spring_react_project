package com.jiraynor.board_back.DTO.request.auth;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignInRequestDto {
    @NotBlank // 이메일 필드는 비어있거나 공백일 수 없습니다.
    private String email;

    @NotBlank // 비밀번호 필드는 비어있거나 공백일 수 없습니다.
    private String password;
}
