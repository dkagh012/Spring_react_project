import BoardItem from "components/BoardItem";
import { SEARCH_PATH } from "constant";
import { latestBoardListMock } from "mocks";
import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { BoardListItem } from "types/interface";
import "./style.css";

export default function Search() {
  // state: searchWord path variable 상태
  const { searchWord } = useParams();

  // 검색 게시물 계수 상태
  const [count, setCount] = useState<number>(0);

  // 검색 게시물 리스트 상태
  const [searchBoardList, setSearchBoardList] = useState<BoardListItem[]>([]);

  // 관련 검색어 리스트 상태
  const [relationList, setRelationList] = useState<string[]>([]);

  const navigate = useNavigate();

  // 연관 검색어 클릭 이벤트 처리
  const onRelationWordClickHandler = (word: string) => {
    navigate(SEARCH_PATH(word));
  };

  useEffect(() => {
    setSearchBoardList(latestBoardListMock);
  }, [searchWord]);

  // render: 검색어 입력 안된 경우 빈 화면 리턴
  if (!searchWord) return <></>;

  return (
    <div id="search-wrapper">
      <div className="search-container">
        <div className="search-title-box">
          <div className="search-title">
            <span className="emphasis">{searchWord}</span>에 대한
            검색결과입니다.
          </div>
          <div className="search-count">{count}</div>
        </div>
        <div className="search-contents-box">
          {count === 0 ? (
            <div className="search-contents-nothing">
              {"검색 결과가 없습니다."}
            </div>
          ) : (
            <div className="search-contents">
              {searchBoardList.map((boardListItem) => (
                <BoardItem boardListItem={boardListItem} />
              ))}
            </div>
          )}
          <div className="search-relation-box">
            <div className="search-relation-card">
              <div className="search-relation-card-container">
                <div className="search-relation-card-title"></div>
                <div className="search-relation-card-contents">
                  {relationList.length === 0 ? (
                    <div className="search-relation-card-contents-nothing">
                      {" "}
                      {"검색 결과가 없습니다."}
                    </div>
                  ) : (
                    <div className="search-relation-card-contents">
                      {relationList.map((word) => (
                        <div
                          className="word-badge"
                          onClick={() => onRelationWordClickHandler(word)}
                        >
                          {word}
                        </div>
                      ))}
                    </div>
                  )}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="search-pagination-box"></div>
    </div>
  );
}
