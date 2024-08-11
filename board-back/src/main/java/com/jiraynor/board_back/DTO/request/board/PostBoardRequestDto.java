package com.jiraynor.board_back.DTO.request.board;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostBoardRequestDto {
    
    // 게시물의 제목을 저장하는 필드입니다. 이 필드는 비어 있을 수 없습니다.
    // @NotBlank 어노테이션은 문자열이 null이 아니고, 공백이 아닌 문자를 포함해야 함을 보장합니다.
    @NotBlank
    private String title;
    
    // 게시물의 내용을 저장하는 필드입니다. 이 필드도 비어 있을 수 없습니다.
    // @NotBlank 어노테이션은 문자열이 null이 아니고, 공백이 아닌 문자를 포함해야 함을 보장합니다.
    @NotBlank
    private String content;
    
    // 게시물에 포함된 이미지 파일들의 경로 또는 URL을 저장하는 리스트입니다.
    // 이 리스트는 null일 수 없으며, 비어 있지 않음을 보장해야 합니다.
    // @NotNull 어노테이션은 리스트가 null이 아님을 보장합니다.
    @NotNull
    private List<String> BoardImageList;
    
    // Lombok 라이브러리의 @NoArgsConstructor 어노테이션은 기본 생성자를 자동으로 생성합니다.
    // 이는 매개변수가 없는 기본 생성자를 필요로 하는 상황에서 유용하게 사용됩니다.
}
