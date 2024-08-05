package com.jiraynor.board_back.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jiraynor.board_back.DTO.request.auth.SignUpRequestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// Lombok 애노테이션을 사용하여 getter 메서드를 자동으로 생성합니다.
@Getter
// Lombok 애노테이션을 사용하여 매개변수가 없는 기본 생성자를 자동으로 생성합니다.
@NoArgsConstructor
// Lombok 애노테이션을 사용하여 모든 필드를 매개변수로 받는 생성자를 자동으로 생성합니다.
@AllArgsConstructor
// JPA 애노테이션을 사용하여 이 클래스가 엔티티 클래스임을 나타냅니다.
@Entity(name = "user")
// JPA 애노테이션을 사용하여 이 클래스가 "user" 테이블과 매핑됨을 나타냅니다.
@Table(name = "user")
public class UserEntity {

    // 이 필드가 기본 키임을 나타냅니다.
    @Id
    private String email;

    // 비밀번호
    private String password;

    // 닉네임
    private String nickname;

    // 전화번호
    private String telNumber;

    // 주소
    private String address;

    // 상세 주소
    private String addressDetail;

    // 프로필 이미지
    private String profileImage;

    // 개인정보 동의 여부
    private boolean agreedPersonal;

    // SignUpRequestDto 객체를 사용하여 엔티티를 초기화하는 생성자
    public UserEntity(SignUpRequestDto dto) {
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.nickname = dto.getNickname();
        this.telNumber = dto.getTelNumber();
        this.address = dto.getAddress();
        this.addressDetail = dto.getAddressDetail();
        this.agreedPersonal = dto.getAgreedPersonal();
    }
}
