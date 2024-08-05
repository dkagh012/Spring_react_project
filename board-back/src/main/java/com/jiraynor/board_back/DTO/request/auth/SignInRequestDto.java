package com.jiraynor.board_back.DTO.request.auth;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Lombok 애노테이션을 사용하여 getter 메서드를 자동으로 생성합니다.
@Getter
// Lombok 애노테이션을 사용하여 setter 메서드를 자동으로 생성합니다.
@Setter
// Lombok 애노테이션을 사용하여 매개변수가 없는 기본 생성자를 자동으로 생성합니다.
@NoArgsConstructor
public class SignInRequestDto {

    // 이메일 필드는 비어있거나 공백일 수 없습니다.
    @NotBlank
    private String email;

    // 비밀번호 필드는 비어있거나 공백일 수 없습니다.
    @NotBlank
    private String password;
}
