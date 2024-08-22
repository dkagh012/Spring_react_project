package com.jiraynor.board_back.service;

import org.springframework.http.ResponseEntity;

import com.jiraynor.board_back.DTO.response.search.GetPopularListResponseDto;

public interface SearchService {

    ResponseEntity<? super GetPopularListResponseDto> getPoplarList();

}
