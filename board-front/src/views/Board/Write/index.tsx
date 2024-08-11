import React, { ChangeEvent, useEffect, useRef, useState } from "react";
import useBoardStore from "stores/board.store";
import "./style.css";
import { useLoginUserStore } from "stores";
import { useNavigate } from "react-router-dom";
import { MAIN_PATH } from "constant";
import { useCookies } from "react-cookie";

export default function BoardWrite() {
  // 타이틀 영역 요소를 참조하는 상태
  const titleRef = useRef<HTMLTextAreaElement | null>(null);
  // 본문 영역 요소를 참조하는 상태
  const contentRef = useRef<HTMLTextAreaElement | null>(null);
  // 이미지 입력 요소를 참조하는 상태
  const imageInputRef = useRef<HTMLInputElement | null>(null);

  // 게시물 관련 상태 관리
  const { title, setTitle } = useBoardStore();
  const { content, setContent } = useBoardStore();
  const { boardImageFileList, setBoardImageFileList } = useBoardStore();
  const { resetBoard } = useBoardStore();

  // 쿠키 상태 관리
  const [cookies, setCookies] = useCookies();

  // 로그인된 유저 상태
  const { loginUser } = useLoginUserStore();

  // 게시물 이미지 미리보기 URL을 관리하는 상태
  const [imageUrls, setImageUrls] = useState<string[]>([]);

  // 페이지 이동을 위한 네비게이트 함수
  const navigate = useNavigate();

  // 제목 변경 이벤트 처리 함수
  const onTitleChangeHandler = (event: ChangeEvent<HTMLTextAreaElement>) => {
    const { value } = event.target;
    setTitle(value);

    if (!titleRef.current) return;
    titleRef.current.style.height = "auto";
    titleRef.current.style.height = `${titleRef.current.scrollHeight}px`;
  };

  // 내용 변경 이벤트 처리 함수
  const onContentChangeHandler = (event: ChangeEvent<HTMLTextAreaElement>) => {
    const { value } = event.target;
    setContent(value);
    if (!contentRef.current) return;
    contentRef.current.style.height = "auto";
    contentRef.current.style.height = `${contentRef.current.scrollHeight}px`;
  };

  // 이미지 변경 이벤트 처리 함수
  const onImageChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
    if (!event.target.files || !event.target.files.length) return;
    const file = event.target.files[0];

    // 새로운 이미지 URL 생성 및 상태 업데이트
    const imageUrl = URL.createObjectURL(file);
    const newImageUrls = imageUrls.map((item) => item);
    newImageUrls.push(imageUrl);
    setImageUrls(newImageUrls);

    // 이미지 파일 리스트 업데이트
    const newBoardImageFileList = boardImageFileList.map((item) => item);
    newBoardImageFileList.push(file);
    setBoardImageFileList(newBoardImageFileList);

    if (!imageInputRef.current) return;
    imageInputRef.current.value = "";
  };

  // 이미지 업로드 버튼 클릭 이벤트 처리 함수
  const onImageUploadButtonClickHandler = () => {
    if (!imageInputRef.current) return;
    imageInputRef.current.click();
  };

  // 이미지 닫기 버튼 클릭 이벤트 처리 함수
  const onImageCloseButtonClickHandler = (deleteIndex: number) => {
    if (!imageInputRef.current) return;
    imageInputRef.current.value = "";

    // 선택된 이미지를 제거하고 상태 업데이트
    const newImageUrls = imageUrls.filter(
      (url, index) => index !== deleteIndex
    );
    setImageUrls(newImageUrls);

    const newBoardImageFileList = boardImageFileList.filter(
      (file, index) => index !== deleteIndex
    );
    setBoardImageFileList(newBoardImageFileList);
  };

  // 컴포넌트 마운트 시 및 특정 조건에서 실행되는 함수
  useEffect(() => {
    const accessToken = cookies.accessToken;
    if (!accessToken) {
      navigate(MAIN_PATH());
      return;
    }
    resetBoard();
  }, []);

  // BoardWrite 컴포넌트의 JSX 반환
  return (
    <div id="board-write-wrapper">
      <div className="board-write-container">
        <div className="board-write-box">
          <div className="board-write-title-box">
            <textarea
              ref={titleRef}
              className="board-write-title-input"
              placeholder="제목을 작성해주세요."
              value={title}
              rows={1}
              onChange={onTitleChangeHandler}
            />
          </div>
          <div className="divider"></div>
          <div className="board-write-content-box">
            <textarea
              ref={contentRef}
              className="board-write-content-textarea"
              placeholder="본문을 작성해주세요."
              value={content}
              onChange={onContentChangeHandler}
            ></textarea>
            <div
              className="icon-button"
              onClick={onImageUploadButtonClickHandler}
            >
              <div className="icon image-box-light-icon"></div>
            </div>
            <input
              ref={imageInputRef}
              type="file"
              accept="image/*"
              style={{ display: "none" }}
              onChange={onImageChangeHandler}
            />
          </div>
          <div className="board-write-images-box">
            {imageUrls.map((imageUrl, index) => (
              <div className="board-write-image-box" key={index}>
                <img className="board-write-image" src={imageUrl} />
                <div
                  className="icon-button image-close"
                  onClick={() => onImageCloseButtonClickHandler(index)}
                >
                  <div className="icon close-icon"></div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
