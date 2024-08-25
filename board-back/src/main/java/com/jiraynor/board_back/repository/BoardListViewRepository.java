package com.jiraynor.board_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jiraynor.board_back.entity.BoardListViewEntity;

import java.util.List;

// 이 인터페이스가 Spring Data JPA 레포지토리임을 나타내는 애노테이션
@Repository
public interface BoardListViewRepository extends JpaRepository<BoardListViewEntity, Integer> {
    // JpaRepository를 상속받아 기본적인 CRUD 작업을 수행할 수 있습니다.
    // BoardListViewEntity: 관리할 엔티티 클래스
    // Integer: 엔티티의 기본 키 타입

    List<BoardListViewEntity> findByOrderByWriteDatetimeDesc();
    List<BoardListViewEntity> findTop3ByWriteDatetimeGreaterThanOrderByFavoriteCountDescCommentCountDescViewCountDescWriteDatetimeDesc(String writeDatetime);

    List<BoardListViewEntity> findByTitleContainsOrContentContainsOrderByWriteDatetimeDesc(String title, String content);

    List<BoardListViewEntity> findByWriterEmailOrderByWriteDatetimeDesc(String writerEmail);
}
