package com.jiraynor.board_back.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;

import com.jiraynor.board_back.DTO.request.board.PostCommentRequestDto;

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
@Entity(name = "comment")
// JPA 애노테이션을 사용하여 이 클래스가 "comment" 테이블과 매핑됨을 나타냅니다.
@Table(name = "comment")
public class CommentEntity {

    // 이 필드가 기본 키임을 나타내며, 기본 키 값을 자동으로 생성함을 나타냅니다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentNumber;

    // 댓글 내용
    private String content;

    // 댓글 작성 시간
    private String writeDatetime;

    // 작성자 이메일
    private String userEmail;

    // 해당 댓글이 속한 게시판 번호
    private int boardNumber;

    public CommentEntity(PostCommentRequestDto dto,Integer boardNumber , String email){
        
        Date now = Date.from(Instant.now());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String writeDatetime = simpleDateFormat.format(now);

        
        this.content = dto.getContent();    
        this.writeDatetime = writeDatetime;
        this.userEmail = email;
        this.boardNumber = boardNumber;
    }
}
