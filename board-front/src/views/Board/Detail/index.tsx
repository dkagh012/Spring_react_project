import React, { ChangeEvent, useEffect, useRef, useState } from "react";
import "./style.css";
import FavoriteItem from "components/FavoriteItem";
import { Board, CommentListItem, FavoriteListItem } from "types/interface";
import { boardMock, commentListMock, favoriteListMock } from "mocks";
import CommentItem from "components/CommentItem";
import Pagination from "components/Pagination";

import defaultProfileImage from "assets/image/default-profile-image.png";
import { useNavigate, useParams } from "react-router-dom";
import { useLoginUserStore } from "stores";
import { BOARD_PATH, BOARD_UPDATE_PATH, MAIN_PATH, USER_PATH } from "constant";
import {
  deleteBoardRequest,
  getBoardRequest,
  getCommentListRequest,
  getFavoriteListRequest,
  increaseViewCountRequest,
  postCommentRequest,
  putFavoriteRequest,
} from "apis";
import GetBoardResponseDto from "apis/response/board/get-board.response.dto";
import { ResponseDto } from "apis/response";
import {
  DeleteBoardResponseDto,
  GetCommentListResponseDto,
  GetFavoriteListResponseDto,
  IncreaseViewCountResponseDto,
  PostCommentResponseDto,
  PutFavoriteResponseDto,
} from "apis/response/board";

import dayjs from "dayjs";
import { useCookies } from "react-cookie";
import { PostCommentRequestDto } from "apis/request/board";
import { usePagination } from "hooks";

export default function BoardDetail() {
  // 게시물 번호 path variable 상태
  const { boardNumber } = useParams();

  // 로그인 유지 상태
  const { loginUser } = useLoginUserStore();

  // 쿠키 상태
  const [cookies, setCookies] = useCookies();

  // 네비게이트 함수
  const navigate = useNavigate();

  // increase view count response 처리 함수
  const increaseViewCountResponse = (
    responseBody: IncreaseViewCountResponseDto | ResponseDto | null
  ) => {
    if (!responseBody) return;
    const { code } = responseBody;

    if (code === "NB") {
      alert("존재하지 않는 게시물입니다.");
      return; // 추가: alert 후 함수를 종료
    }
    if (code === "DBE") {
      alert("데이터베이스 오류 입니다.");
      return; // 추가: alert 후 함수를 종료
    }
  };

  // 게시물 상세 상단 컴포넌트
  const BoardDetailTop = () => {
    const [isWriter, setWriter] = useState<boolean>(false);

    const [showMore, setShowMore] = useState<boolean>(false);

    const [board, setBoard] = useState<Board | null>(null);

    // 작성열 포맷 변경 함수
    const getWriteDatetimeFormat = () => {
      if (!board) return "";
      const date = dayjs(board.writeDatetime);
      return date.format("YYYY. MM. DD.");
    };

    //  get board response 처리 함수
    const getBoardResponse = (
      responseBody: GetBoardResponseDto | ResponseDto | null
    ) => {
      const data = (responseBody as any).data || responseBody;

      const code = data.code;

      if (code === "NB") {
        alert("존재하지 않는 게시물입니다");
        navigate(MAIN_PATH());
        return; // 추가: alert 후 함수를 종료
      }
      if (code === "DBE") {
        alert("데이터베이스 오류입니다.");
        navigate(MAIN_PATH());
        return; // 추가: alert 후 함수를 종료
      }
      if (code !== "SU") {
        navigate(MAIN_PATH());
        return;
      }

      const board: Board = { ...(responseBody as GetBoardResponseDto) };
      setBoard(board);

      if (!loginUser) {
        setWriter(false);
        return;
      }
      const isWriter = loginUser.email === board.writerEmail;
      setWriter(isWriter);
    };

    const deleteBoardResponse = (
      responseBody: DeleteBoardResponseDto | ResponseDto | null
    ) => {
      if (!responseBody) return;
      const { code } = responseBody;
      if (code === "VF") {
        alert("잘못된 접근입니다.");
        return; // 추가: alert 후 함수를 종료
      }
      if (code === "NU") {
        alert("존재하지 않는 유저입니다.");
        return; // 추가: alert 후 함수를 종료
      }
      if (code === "NB") {
        alert("존재하지 않는 게시물입니다.");
        return; // 추가: alert 후 함수를 종료
      }
      if (code === "AF") {
        alert("인증에 실패했습니다.");
        return; // 추가: alert 후 함수를 종료
      }
      if (code === "NP") {
        alert("권한이 없습니다.");
        return; // 추가: alert 후 함수를 종료
      }
      if (code === "DBE") {
        alert("데이터베이스 오류입니다.");
        return; // 추가: alert 후 함수를 종료
      }
      if (code !== "SU") return;

      navigate(MAIN_PATH());
    };

    // 닉네임 클릭 이벤트 처리
    const onNicknameClickHandler = () => {
      if (!board) return;
      navigate(USER_PATH(board.writerEmail));
    };

    const onMoreButtonClickHandler = () => {
      setShowMore(!showMore);
    };

    const onUpdateButtonClickHandler = () => {
      if (!board || !loginUser) return;
      if (loginUser.email !== board.writerEmail) return;
      navigate(BOARD_PATH() + "/" + BOARD_UPDATE_PATH(board.boardNumber));
    };
    const onDeleteButtonClickHandler = () => {
      if (!board || !loginUser || !boardNumber || !cookies.accessToken) return;
      if (loginUser.email !== board.writerEmail) return;

      deleteBoardRequest(boardNumber, cookies.accessToken).then(
        deleteBoardResponse
      );
    };

    useEffect(() => {
      if (!boardNumber) {
        navigate(MAIN_PATH());
        return;
      }

      getBoardRequest(boardNumber).then(getBoardResponse);
    }, [boardNumber]);
    // 게시물 상세 상단 컴포넌트 랜더링
    // if (!board) return <></>;
    return (
      <div id="board-detail-top">
        <div className="board-detail-top-header">
          <div className="board-detail-title">{board?.title}</div>
          <div className="board-detail-top-sub-box">
            <div className="board-detail-write-info-box">
              <div
                className="board-detail-writer-profile-image"
                style={{
                  backgroundImage: `url(${
                    board?.writerProfileImage
                      ? board.writerProfileImage
                      : defaultProfileImage
                  })`,
                }}
              ></div>
              <div
                className="board-detail-writer-nickname"
                onClick={onNicknameClickHandler}
              >
                {board?.writerNickname}
              </div>
              <div className="board-detail-info-divider">{"|"}</div>
              <div className="board-detail-write-date">
                {getWriteDatetimeFormat()}
              </div>
            </div>
            {isWriter && (
              <div className="icon-button" onClick={onMoreButtonClickHandler}>
                <div className="icon more-icon"></div>
              </div>
            )}

            {showMore && (
              <div className="board-detail-more-box">
                <div
                  className="board-detail-update-button"
                  onClick={onUpdateButtonClickHandler}
                >
                  {"수정"}
                </div>
                <div className="divider"></div>
                <div
                  className="board-detail-delete-button"
                  onClick={onDeleteButtonClickHandler}
                >
                  {"삭제"}
                </div>
              </div>
            )}
          </div>
        </div>
        <div className="divider"></div>
        <div className="board-detail-top-main">
          <div className="board-detail-main-text">{board?.content}</div>

          {board?.boardImageList.map((image) => (
            <img className="board-detail-main-image" src={image} />
          ))}
        </div>
      </div>
    );
  };
  // 게시물 상세 하단 컴포넌트
  const BoardDetailBottom = () => {
    // 댓글 textarea 장소 상태
    const commentRef = useRef<HTMLTextAreaElement | null>(null);

    // 페이지 네이션 관련 상태
    const {
      currentPage, // 현재 페이지 번호
      setCurrentPage, // 현재 페이지 번호를 설정하는 함수
      currentSection, // 현재 섹션 번호
      setCurrentSection, // 현재 섹션 번호를 설정하는 함수
      viewList, // 현재 페이지에 보여줄 객체 리스트
      viewPageList, // 현재 섹션에 보여줄 페이지 리스트
      totalSection, // 전체 섹션 수
      setTotalList, // 전체 객체 리스트를 설정하는 함수
    } = usePagination<CommentListItem>(3);

    // 좋아요 리스트 상태
    const [favoriteList, setFavoriteList] = useState<FavoriteListItem[]>([]);
    // 좋아요 상태
    const [isFavorite, setFavorite] = useState<boolean>(false);
    // 좋아요 상자 보기 상태
    const [showFavorite, setShowFavorite] = useState<boolean>(false);
    // 전체 댓글 개수 상태
    const [totalCommentCount, setTotalCommentCount] = useState<number>(0);

    // 댓글 상태
    const [comment, setComment] = useState<string>("");
    // 댓글 상자 보기 상태
    const [showComment, setShowComment] = useState<boolean>(false);

    // get favorite list response 처리 함수
    const getFavoriteListResponse = (
      responseBody: GetBoardResponseDto | ResponseDto | null
    ) => {
      if (!responseBody) return;
      const { code } = responseBody;
      if (code === "NB") {
        alert("존재하지 않는 게시물 입니다.");
        return; // 추가: alert 후 함수를 종료
      }
      if (code === "DBE") {
        alert("데이터베이스 오류입니다.");
        return; // 추가: alert 후 함수를 종료
      }
      if (code !== "SU") return;

      const { favoriteList } = responseBody as GetFavoriteListResponseDto;
      setFavoriteList(favoriteList);

      if (!loginUser) {
        setFavorite(false);
        return;
      }

      const isFavorite =
        favoriteList.findIndex(
          (favorite) => favorite.email === loginUser.email
        ) !== -1;
      setFavorite(isFavorite);
    };

    // get comment list response 처리 함수
    const getCommentListResponse = (
      responseBody: GetCommentListResponseDto | ResponseDto | null
    ) => {
      if (!responseBody) return;
      const { code } = responseBody;
      if (code === "NB") {
        alert("존재하지 않는 게시물 입니다.");
        return; // 추가: alert 후 함수를 종료
      }
      if (code === "DBE") {
        alert("데이터베이스 오류입니다.");
        return; // 추가: alert 후 함수를 종료
      }
      if (code !== "SU") return;

      const { commentList } = responseBody as GetCommentListResponseDto;
      setTotalList(commentList);
      setTotalCommentCount(commentList.length);
    };

    // put favorite response 처리 함수
    const putFavoriteResponse = (
      responseBody: PutFavoriteResponseDto | ResponseDto | null
    ) => {
      if (!responseBody) return;
      const { code } = responseBody;
      if (code === "VF") {
        alert("잘못된 접근입니다.");
        return; // 추가: alert 후 함수를 종료
      }
      if (code === "NU") {
        alert("존재하지 않는 유저입니다.");
        return; // 추가: alert 후 함수를 종료
      }
      if (code === "NB") {
        alert("존재하지 않는 게시물입니다.");
        return; // 추가: alert 후 함수를 종료
      }
      if (code === "AF") {
        alert("인증이 실패했습니다.");
        return; // 추가: alert 후 함수를 종료
      }
      if (code === "DBE") {
        alert("데이터베이스 오류입니다.");
        return; // 추가: alert 후 함수를 종료
      }
      if (code !== "SU") return;

      if (!boardNumber) return;
      getFavoriteListRequest(boardNumber).then(getFavoriteListResponse);
    };

    const postCommentResponse = (
      responseBody: PostCommentResponseDto | ResponseDto | null
    ) => {
      if (!responseBody) return;
      const { code } = responseBody;
      if (code === "VF") {
        alert("잘못된 접근입니다.");
        return; // 추가: alert 후 함수를 종료
      }
      if (code === "NU") {
        alert("존재하지 않는 유저입니다.");
        return; // 추가: alert 후 함수를 종료
      }
      if (code === "NB") {
        alert("존재하지 않는 게시물입니다.");
        return; // 추가: alert 후 함수를 종료
      }
      if (code === "AF") {
        alert("인증이 실패했습니다.");
        return; // 추가: alert 후 함수를 종료
      }
      if (code === "DBE") {
        alert("데이터베이스 오류입니다.");
        return; // 추가: alert 후 함수를 종료
      }
      if (code !== "SU") return;

      setComment("");

      if (!boardNumber) return;
      getCommentListRequest(boardNumber).then(getCommentListResponse);
    };

    // 좋아요 클릭 이벤트 처리
    const onFavoriteClickHandler = () => {
      if (!boardNumber || !loginUser || !cookies.accessToken) return;
      putFavoriteRequest(boardNumber, cookies.accessToken).then(
        putFavoriteResponse
      );
    };
    // 좋아요 상자 보기 클릭 이벤트 처리
    const onShowFavoriteClickHandler = () => {
      setShowFavorite(!showFavorite);
    };
    // 댓글 상자 보기 클릭 이벤트 처리
    const onShowCommentClickHandler = () => {
      setShowComment(!showComment);
    };
    // 댓글 작성 버튼 클릭 이벤트 처리
    const onCommentSubmitButtonClickHandler = () => {
      if (!comment || !boardNumber || !loginUser || !cookies.accessToken)
        return;

      const requestBody: PostCommentRequestDto = { content: comment };
      postCommentRequest(boardNumber, requestBody, cookies.accessToken).then(
        postCommentResponse
      );
    };
    // 댓글 변경 이벤트 처리
    const onCommentChangeHandler = (
      event: ChangeEvent<HTMLTextAreaElement>
    ) => {
      const { value } = event.target;
      setComment(value);
      if (!commentRef.current) return;
      commentRef.current.style.height = "auto";
      commentRef.current.style.height = `${commentRef.current.scrollHeight}px`;
    };

    // 게시물 번호 path variable이 바뀔떄 마다 좋아요 및 댓글리스트 불러오기
    useEffect(() => {
      if (!boardNumber) return;
      getFavoriteListRequest(boardNumber).then(getFavoriteListResponse);
      getCommentListRequest(boardNumber).then(getCommentListResponse);
    }, [boardNumber]);

    // 게시물 상세 하단 컴포넌트 랜더링
    return (
      <div id="board-detail-bottom">
        <div className="board-detail-bottom-button-box">
          <div className="board-detail-bottom-button-group">
            <div className="icon-button" onClick={onFavoriteClickHandler}>
              {isFavorite ? (
                <div className="icon favorite-fill-icon"></div>
              ) : (
                <div className="icon favorite-light-icon"></div>
              )}
            </div>
            <div className="board-detail-bottom-button-text">
              <div>{`좋아요 ${favoriteList.length}`}</div>
            </div>
            <div className="icon-button" onClick={onShowFavoriteClickHandler}>
              {showFavorite ? (
                <div className="icon up-light-icon"></div>
              ) : (
                <div className="icon down-light-icon"></div>
              )}
            </div>
          </div>
          <div className="board-detail-bottom-button-group">
            <div className="icon-button">
              <div className="icon comment-icon"></div>
            </div>
            <div className="board-detail-bottom-button-text">
              <div>{`댓글  ${totalCommentCount}`}</div>
            </div>
            <div className="icon-button" onClick={onShowCommentClickHandler}>
              {showComment ? (
                <div className="icon up-light-icon"></div>
              ) : (
                <div className="icon down-light-icon"></div>
              )}
            </div>
          </div>
        </div>
        {showFavorite && (
          <div className="board-detail-bottom-favorite-box">
            <div className="board-detail-bottom-favorite-container">
              <div className="board-detail-bottom-favorite-title">
                {"좋아요"}
                <span className="emphasis">{favoriteList.length}</span>
              </div>
              <div className="board-detail-bottom-favorite-contents">
                {favoriteList.map((item) => (
                  <FavoriteItem favoriteListItem={item} />
                ))}
              </div>
            </div>
          </div>
        )}
        {showComment && (
          <div className="board-detail-bottom-comment-box">
            <div className="board-detail-bottom-comment-container">
              <div className="board-detail-bottom-comment-title">
                {"댓글"}
                <span className="emphasis">{totalCommentCount}</span>
              </div>
              <div className="board-detail-bottom-comment-list-container">
                {viewList.map((item) => (
                  <CommentItem commentListItem={item} />
                ))}
              </div>
            </div>
            <div className="divider"></div>
            <div className="board-detail-bottom-comment-pagination-box">
              <Pagination
                currentPage={currentPage}
                currentSection={currentSection}
                SetCurrentPage={setCurrentPage}
                SetCurrentSection={setCurrentSection}
                viewPageList={viewPageList}
                totalSection={totalSection}
              />
            </div>
            {loginUser !== null && (
              <div className="board-detail-bottom-comment-input-box">
                <div className="board-detail-bottom-comment-input-container">
                  <textarea
                    ref={commentRef}
                    className="board-detail-bottom-comment-textarea"
                    placeholder="댓글을 작성해주세요."
                    value={comment}
                    onChange={onCommentChangeHandler}
                  />
                  <div className="board-detail-bottom-comment-button-box">
                    <div
                      className={
                        comment === "" ? "disable-button" : "black-button"
                      }
                      onClick={onCommentSubmitButtonClickHandler}
                    >
                      {"댓글달기"}
                    </div>
                  </div>
                </div>
              </div>
            )}
          </div>
        )}
      </div>
    );
  };

  let effectFlag = true;
  useEffect(() => {
    if (!boardNumber) return;
    if (effectFlag) {
      effectFlag = false;
      return;
    }
    increaseViewCountRequest(boardNumber).then(increaseViewCountResponse);
  }, [boardNumber]);

  // 게시물 상세 화면 컴포넌트 랜더링
  return (
    <div id="board-detail-wrapper">
      <div className="board-detail-container">
        <BoardDetailTop />
        <BoardDetailBottom />
      </div>
    </div>
  );
}
