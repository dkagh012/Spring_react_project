package com.jiraynor.board_back.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Entity(name = "image")
// JPA 애노테이션을 사용하여 이 클래스가 "image" 테이블과 매핑됨을 나타냅니다.
@Table(name = "image")
public class ImageEntity {

    // 이 필드가 기본 키임을 나타내며, 기본 키 값을 자동으로 생성함을 나타냅니다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sequence;

    // 게시판 번호
    private int boardNumber;

    // 이미지 경로 또는 URL
    private String image;
}
