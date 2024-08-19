import React, { Dispatch, SetStateAction } from "react";
import "./style.css";

// 페이지 네이션 컴포넌트 Properties
interface Props {
  currentPage: number;
  currentSection: number;
  SetCurrentPage: Dispatch<SetStateAction<number>>;
  SetCurrentSection: Dispatch<SetStateAction<number>>;

  viewPageList: number[];
  totalSection: number;
}

// 페이지네이션 컴포넌트
export default function Pagination(props: Props) {
  // Properties
  const { currentPage, currentSection, viewPageList, totalSection } = props;
  const { SetCurrentPage, SetCurrentSection } = props;

  // 페이지 클릭 이벤트 처리
  const onPageClickHandler = (page: number) => {
    SetCurrentPage(page);
  };

  // 이전 클릭 이벤트 처리
  const onPreviousClickHandler = () => {
    if (currentSection === 1) return;
    SetCurrentPage((currentSection - 1) * 10);
    SetCurrentSection(currentSection - 1);
  };

  // 다음 클릭 이벤트 처리
  const onNextClickHandler = () => {
    if (currentSection === totalSection) return;
    SetCurrentPage(currentSection * 10 + 1);
    SetCurrentSection(currentSection + 1);
  };
  // 게시물 상세 하단 컴포넌트 랜더링
  return (
    <div id="pagination-wrapper">
      <div className="pagination-change-link-box">
        <div className="icon-box-small">
          <div className="icon expand-left-icon"></div>
        </div>
        <div
          className="pagination-change-link-text"
          onClick={onPreviousClickHandler}
        >
          {"이전"}
        </div>
      </div>
      <div className="pagination-divider">{"|"}</div>

      {viewPageList.map((page) => {
        return page === currentPage ? (
          <div className="pagination-text-active">{page}</div>
        ) : (
          <div
            className="pagination-text"
            onClick={() => onPageClickHandler(page)}
          >
            {page}
          </div>
        );
      })}

      <div className="pagination-divider">{"|"}</div>
      <div className="pagination-change-link-box">
        <div
          className="pagination-change-link-text"
          onClick={onNextClickHandler}
        >
          {"다음"}
        </div>
        <div className="icon-box-small">
          <div className="icon expand-right-icon"></div>
        </div>
      </div>
    </div>
  );
}
