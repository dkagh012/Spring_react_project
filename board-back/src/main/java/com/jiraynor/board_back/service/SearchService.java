package com.jiraynor.board_back.service;

import org.springframework.http.ResponseEntity;

import com.jiraynor.board_back.DTO.response.search.GetPopularListResponseDto;
import com.jiraynor.board_back.DTO.response.search.GetRelationListResponseDto;

public interface SearchService {

    ResponseEntity<? super GetPopularListResponseDto> getPoplarList();
    ResponseEntity<? super GetRelationListResponseDto> getRelationList(String searchWord);
}
