package com.jiraynor.board_back.DTO.response.search;

import com.jiraynor.board_back.DTO.response.ResponseDto;
import com.jiraynor.board_back.common.ResponseCode;
import com.jiraynor.board_back.common.ResponseMessage;
import com.jiraynor.board_back.repository.resultSet.GetPopularListResultSet;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class GetPopularListResponseDto extends ResponseDto{
    
    private List<String> popularWordList;

    private GetPopularListResponseDto(List<GetPopularListResultSet> resultSets){
        super(ResponseCode.SUCCESS,ResponseMessage.SUCCESS);

        List<String> popularWordList = new ArrayList<>();
        for (GetPopularListResultSet resultSet: resultSets){
            String popularWord = resultSet.getSearchWord();
                popularWordList.add(popularWord);
        }
        this.popularWordList= popularWordList;

    }

    public static ResponseEntity<GetPopularListResponseDto> success(List<GetPopularListResultSet> resultSets){
        GetPopularListResponseDto result = new GetPopularListResponseDto(resultSets);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
