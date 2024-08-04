package com.jiraynor.board_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jiraynor.board_back.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    boolean existsByEmail(String email); // 이메일로 사용자 존재 여부 확인

    boolean existsByNickname(String nickname); // 닉네임으로 사용자 존재 여부 확인

    boolean existsByTelNumber(String telNumber); // 전화번호로 사용자 존재 여부 확인

    UserEntity findByEmail(String email); // 이메일로 사용자 조회
}
