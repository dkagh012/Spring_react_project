import React, {
  ChangeEvent,
  useRef,
  useState,
  KeyboardEvent,
  useEffect,
} from "react";
import "./style.css";
import { useNavigate, useParams } from "react-router-dom";
import { MAIN_PATH, SEARCH_PATH } from "constant";

// Header 컴포넌트 정의
export default function Header() {
  // 네비게이트 함수
  const navigate = useNavigate();

  // 로고 클릭 이벤트 처리 함수
  const onLogoClickHandler = () => {
    navigate(MAIN_PATH()); // MAIN_PATH()로 네비게이트
  };

  // 검색 버튼 컴포넌트
  const SearchButton = () => {
    // 검색어 입력 요소 참조 상태
    const searchButtonRef = useRef<HTMLDivElement | null>(null);
    // 검색어 버튼 상태
    const [status, setStatus] = useState<boolean>(false);
    // 검색어 상태
    const [word, setWord] = useState<string>("");

    // URL 파라미터에서 검색어 가져오기
    const { searchWord } = useParams();

    // 검색어 입력 변경 핸들러
    const onSearchWordChangeHandler = (
      event: ChangeEvent<HTMLInputElement>
    ) => {
      const value = event.target.value;
      setWord(value); // 검색어 상태 업데이트
    };

    // 검색어 입력 키 다운 핸들러
    const onSearchWordKeyDownHandler = (
      event: KeyboardEvent<HTMLInputElement>
    ) => {
      if (event.key !== "Enter") return; // Enter 키가 아니면 무시
      if (!searchButtonRef.current) return; // 버튼 참조가 없으면 무시
      searchButtonRef.current.click(); // 버튼 클릭 트리거
    };

    // 검색 버튼 클릭 핸들러
    const onSearchButtonClickHandler = () => {
      if (!status) {
        setStatus(!status); // 상태 토글
        return;
      }

      navigate(SEARCH_PATH(word)); // 검색 경로로 네비게이트
    };

    // 컴포넌트 마운트 시 및 searchWord 변경 시 실행
    useEffect(() => {
      if (searchWord) {
        setWord(searchWord); // 검색어 상태 설정
        setStatus(true); // 상태 설정
      }
    }, [searchWord]);

    // 검색 입력 상태가 아닐 때 아이콘 버튼 반환
    if (!status) {
      return (
        <div className="icon-button" onClick={onSearchButtonClickHandler}>
          <div className="icon search-light-icon"></div>
        </div>
      );
    }

    // 검색 입력 상태일 때 입력 박스 반환
    return (
      <div className="header-search-input-box">
        <input
          className="header-search-input"
          type="text"
          placeholder="검색어를 입력해주세요."
          value={word}
          onChange={onSearchWordChangeHandler}
          onKeyDown={onSearchWordKeyDownHandler}
        />
        <div className="icon-button" onClick={onSearchButtonClickHandler}>
          <div className="icon search-light-icon"></div>
        </div>
      </div>
    );
  };

  // Header 컴포넌트의 JSX 반환
  return (
    <div id="header">
      <div className="header-container">
        <div className="header-left-box" onClick={onLogoClickHandler}>
          <div className="icon-box">
            <div className="icon logo-dark-icon"></div>
          </div>
          <div className="header-logo">{"Hoons Board"}</div>
        </div>
        <div className="header-right-box">
          <SearchButton />
        </div>
      </div>
    </div>
  );
}
