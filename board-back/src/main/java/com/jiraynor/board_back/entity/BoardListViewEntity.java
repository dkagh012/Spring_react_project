package com.jiraynor.board_back.entity;

import javax.persistence.Entity;
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
@Entity(name = "board_list_view")
// JPA 애노테이션을 사용하여 이 클래스가 "board_list_view" 테이블과 매핑됨을 나타냅니다.
@Table(name = "board_list_view")
public class BoardListViewEntity {

    // 이 필드가 기본 키임을 나타냅니다.
    @Id
    private int boardNumber;

    // 게시글 제목
    private String title;

    // 게시글 내용
    private String content;

    // 게시글 타이틀 이미지
    private String titleImage;

    // 조회수
    private int viewCount;

    // 즐겨찾기 수
    private int favoriteCount;

    // 댓글 수
    private int commentCount;

    // 작성 시간
    private String writeDatetime;

    // 작성자 이메일
    private String writerEmail;

    // 작성자 닉네임
    private String writerNickname;

    // 작성자 프로필 이미지
    private String writerProfileImage;
}
