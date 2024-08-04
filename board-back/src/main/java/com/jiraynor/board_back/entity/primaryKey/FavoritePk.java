package com.jiraynor.board_back.entity.primaryKey;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoritePk {
    @Column(name="user_email")
    private String userEmail;
    @Column(name="board_number")
    private int boardNumber;
}
