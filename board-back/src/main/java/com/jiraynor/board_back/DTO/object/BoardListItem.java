package com.jiraynor.board_back.DTO.object;

import com.jiraynor.board_back.entity.BoardListViewEntity;

import java.util.List;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// Lombok 애노테이션을 사용하여 getter 메서드를 자동으로 생성합니다.
@Getter
// Lombok 애노테이션을 사용하여 매개변수가 없는 기본 생성자를 자동으로 생성합니다.
@NoArgsConstructor
// Lombok 애노테이션을 사용하여 모든 필드를 매개변수로 받는 생성자를 자동으로 생성합니다.
@AllArgsConstructor
public class BoardListItem {

    // 게시판 번호
    private int boardNumber;
    // 게시글 제목
    private String title;
    // 게시글 내용
    private String content;
    // 게시글 타이틀 이미지
    private String boardTitleImage;
    // 즐겨찾기 수
    private int favoriteCount;
    // 댓글 수
    private int commentCount;
    // 조회수
    private int viewCount;
    // 작성 시간
    private String writeDatetime;
    // 작성자 닉네임
    private String writerNickname;
    // 작성자 프로필 이미지
    private String writerProfileImage;

    public BoardListItem(BoardListViewEntity boardListViewEntity){
            // 게시판 번호
      this.boardNumber = boardListViewEntity.getBoardNumber();
    // 게시글 제목
      this.title = boardListViewEntity.getTitle();
    // 게시글 내용
      this.content= boardListViewEntity.getContent();
    // 게시글 타이틀 이미지
      this.boardTitleImage= boardListViewEntity.getTitleImage();
    // 즐겨찾기 수
      this.favoriteCount= boardListViewEntity.getFavoriteCount();
    // 댓글 수
      this.commentCount= boardListViewEntity.getCommentCount();
    // 조회수
      this.viewCount= boardListViewEntity.getViewCount();
    // 작성 시간
      this.writeDatetime= boardListViewEntity.getWriteDatetime();
    // 작성자 닉네임
      this.writerNickname= boardListViewEntity.getWriterNickname();
    // 작성자 프로필 이미지
      this.writerProfileImage= boardListViewEntity.getWriterProfileImage();
    }

    public static List<BoardListItem> getList(List<BoardListViewEntity> boardListViewEntities){
        List<BoardListItem> list = new ArrayList<>();
        for(BoardListViewEntity boardListViewEntity: boardListViewEntities){
            BoardListItem boardListItem = new BoardListItem(boardListViewEntity);
            list.add(boardListItem);
        }
        return list;
        
    }
}
