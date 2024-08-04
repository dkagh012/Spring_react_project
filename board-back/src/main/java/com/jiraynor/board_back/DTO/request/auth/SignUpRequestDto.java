package com.jiraynor.board_back.DTO.request.auth;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequestDto {
    
    @NotBlank @Email
    private String email; // 이메일 주소. 공백이 아니며 이메일 형식이어야 합니다.

    @NotBlank @Size(min=8,max=20)
    private String password; // 비밀번호. 공백이 아니며 길이는 8자에서 20자 사이여야 합니다.

    @NotBlank
    private String nickname; // 닉네임. 공백이 아니어야 합니다.

    @NotBlank @Pattern(regexp="^[0-9]{11,13}$")
    private String telNumber; // 전화번호. 공백이 아니며 11자리에서 13자리 사이의 숫자 형식이어야 합니다.

    @NotBlank
    private String address; // 주소. 공백이 아니어야 합니다.

    private String addressDetail; // 상세 주소. 필수 항목은 아닙니다.

    @NotNull @AssertTrue
    private Boolean agreedPersonal; // 개인정보 동의 여부. null이 아니어야 하며 true여야 합니다.
}
