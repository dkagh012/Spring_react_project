package com.jiraynor.board_back.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jiraynor.board_back.entity.CommentEntity;
import com.jiraynor.board_back.repository.resultSet.GetCommentListResultSet;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer>{

    @Query(
        value = 
        "SELECT " +
            "U.nickname AS nickname, " +
            "U.profile_image AS profile_image, " +
            "C.write_datetime AS writeDatetime, " +
            "C.content AS content " +
        "FROM comment AS C " +
        "INNER JOIN user AS U " +
        "ON C.user_email = U.email " +
        "WHERE C.board_number = ?1 " +
        "ORDER BY writeDatetime DESC" ,
        nativeQuery = true
    )

    List<GetCommentListResultSet> getCommentList(Integer boardNumber);

        @Transactional
    void deleteByBoardNumber(Integer boardNumber);
}