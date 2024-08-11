package com.jiraynor.board_back.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;
import java.time.Instant;
import java.text.SimpleDateFormat;

import com.jiraynor.board_back.DTO.request.board.PostBoardRequestDto;

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
@Entity(name = "board")
// JPA 애노테이션을 사용하여 이 클래스가 "board" 테이블과 매핑됨을 나타냅니다.
@Table(name = "board")
public class BoardEntity {

    // 이 필드가 기본 키임을 나타내며, 기본 키 값을 자동으로 생성함을 나타냅니다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardNumber;
    
    // 게시글 제목을 나타내는 필드입니다.
    private String title;
    
    // 게시글 내용을 나타내는 필드입니다.
    private String content;
    
    // 작성 시간을 문자열 형식으로 저장하는 필드입니다.
    private String writeDatetime;
    
    // 게시글의 즐겨찾기 수를 나타내는 필드입니다.
    private int favoriteCount;
    
    // 게시글의 댓글 수를 나타내는 필드입니다.
    private int commentCount;
    
    // 게시글의 조회수를 나타내는 필드입니다.
    private int viewCount;
    
    // 작성자의 이메일을 나타내는 필드입니다.
    private String writerEmail;

    // PostBoardRequestDto 객체와 작성자의 이메일을 이용하여 새로운 게시글 엔티티를 생성하는 생성자입니다.
    public BoardEntity(PostBoardRequestDto dto, String email){
        
        // 현재 시간을 가져와 Date 객체로 변환합니다.
        Date now = Date.from(Instant.now());
        
        // 날짜와 시간을 "yyyy-MM-dd HH:mm:ss" 형식으로 포맷팅하기 위한 SimpleDateFormat 객체를 생성합니다.
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 현재 시간을 포맷팅하여 문자열로 변환합니다.
        String writeDatetime= simpleDateFormat.format(now);
        
        // DTO에서 제목과 내용을 가져와 필드에 설정합니다.
        this.title = dto.getTitle();
        this.content = dto.getContent();
        
        // 포맷팅된 작성 시간을 필드에 설정합니다.
        this.writeDatetime = writeDatetime;
        
        // 기본값으로 초기화하는 필드들입니다. 즐겨찾기 수, 댓글 수, 조회수는 모두 0으로 설정합니다.
        this.favoriteCount = 0;
        this.commentCount = 0;
        this.viewCount = 0;
        
        // 작성자의 이메일을 필드에 설정합니다.
        this.writerEmail = email;
    }

    public void increaseViewCount(){
        this.viewCount++;
    }
}
