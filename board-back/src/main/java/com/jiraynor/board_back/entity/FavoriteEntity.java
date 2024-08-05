package com.jiraynor.board_back.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.jiraynor.board_back.entity.primaryKey.FavoritePk;

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
@Entity(name = "favorite")
// JPA 애노테이션을 사용하여 이 클래스가 "favorite" 테이블과 매핑됨을 나타냅니다.
@Table(name = "favorite")
// JPA 애노테이션을 사용하여 복합 기본 키 클래스를 지정합니다.
@IdClass(FavoritePk.class)
public class FavoriteEntity {

    // 복합 기본 키의 첫 번째 필드인 사용자 이메일
    @Id
    private String userEmail;

    // 복합 기본 키의 두 번째 필드인 게시판 번호
    @Id
    private int boardNumber;
}
