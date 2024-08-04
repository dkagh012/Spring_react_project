import React, {
  ChangeEvent,
  useRef,
  useState,
  KeyboardEvent,
  useEffect,
} from "react";
import "./style.css";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import {
  AUTH_PATH,
  BOARD_PATH,
  BOARD_DETAIL_PATH,
  BOARD_WRITE_PATH,
  BOARD_UPDATE_PATH,
  MAIN_PATH,
  SEARCH_PATH,
  USER_PATH,
} from "constant";
import { useCookies } from "react-cookie";
import { useBoardStore, useLoginUserStore } from "stores";
import path from "path";

// Header 컴포넌트 정의
export default function Header() {
  // 로그인 유저 상태
  const { loginUser, setLoginUser, resetLoginUser } = useLoginUserStore();

  // path 상태
  const { pathname } = useLocation();

  // cookie 상태
  const [cookie, setCookie] = useCookies();

  // 로그인 상태
  const [isLogin, setLogin] = useState<boolean>(false);

  // 인증 페이지 상태
  const [isAuthPage, setAuthPage] = useState<boolean>(false);
  // 메인 페이지 상태
  const [isMainPage, setMainPage] = useState<boolean>(false);
  // 검색 페이지 상태
  const [isSearchPage, setSearchPage] = useState<boolean>(false);
  // 게시물 상세 페이지 상태
  const [isBoardDetailPage, setBoardDetailPage] = useState<boolean>(false);
  // 게시물 작성 페이지 상태
  const [isBoardWritePage, setBoardWritePage] = useState<boolean>(false);
  // 게시물 수정 페이지 상태
  const [isBoardUpdatePage, setBoardUpdatePage] = useState<boolean>(false);
  // 유저 페이지 상태
  const [isUserPage, setUserPage] = useState<boolean>(false);

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

  // 로그인 또는 마이페이지 버튼 컴포넌트
  const MyPageButton = () => {
    // userEmail path variable 상태
    const { userEmail } = useParams();

    const onMyPageButtonClickHandler = () => {
      if (!loginUser) return;
      const { email } = loginUser;
      navigate(USER_PATH(email));
    };
    const onSignOutButtonClickHandler = () => {
      resetLoginUser();
      navigate(MAIN_PATH());
    };
    const onSignInButtonClickHandler = () => {
      navigate(AUTH_PATH());
    };
    if (isLogin && userEmail === loginUser?.email)
      // 마이페이지 버튼 컴포넌트 렌더링
      return (
        <div className="white-button" onClick={onMyPageButtonClickHandler}>
          {"마이페이지"}
        </div>
      );
    // 로그아웃 버튼 컴포넌트 렌더링
    if (isLogin)
      return (
        <div className="white-button" onClick={onSignOutButtonClickHandler}>
          {"로그아웃"}
        </div>
      );
    // 로그인 버튼 컴포넌트 렌더링
    return (
      <div className="black-button" onClick={onSignInButtonClickHandler}>
        {"로그인"}
      </div>
    );
  };

  // 업로드 버튼 컴포넌트
  const UploadButton = () => {
    // 게시물 상태
    const { title, content, boardImageFileList, resetBoard } = useBoardStore();

    // 업로드 버튼 클릭 이벤트 처리 함수
    const onUploadButtonClickHandler = () => {};

    // if (title && content)
    //   return (
    //     <div className="black-button" onClick={onUploadButtonClickHandler}>
    //       {"업로드"}
    //     </div>
    //   );
    return <div className="disable-button">{"업로드"}</div>;
  };

  // path가 변경될 때 마다 실행될 함수
  useEffect(() => {
    const isAuthPage = pathname.startsWith(AUTH_PATH()); // 경로가 AUTH_PATH()로 시작하면 인증 페이지임을 나타냅니다.
    setAuthPage(isAuthPage); // isAuthPage 상태를 업데이트합니다.

    const isMainPage = pathname === MAIN_PATH(); // 경로가 MAIN_PATH()와 일치하면 메인 페이지임을 나타냅니다.
    setMainPage(isMainPage); // isMainPage 상태를 업데이트합니다.

    const isSearchPage = pathname.startsWith(SEARCH_PATH("")); // 경로가 SEARCH_PATH('')로 시작하면 검색 페이지임을 나타냅니다.
    setSearchPage(isSearchPage); // isSearchPage 상태를 업데이트합니다.

    const isBoardDetailPage = pathname.startsWith(
      BOARD_PATH() + "/" + BOARD_DETAIL_PATH("")
    );
    // 경로가 BOARD_PATH() + '/' + BOARD_DETAIL_PATH('')로 시작하면 게시판 상세 페이지임을 나타냅니다.
    setBoardDetailPage(isBoardDetailPage); // isBoardDetailPage 상태를 업데이트합니다.

    const isBoardWritePage = pathname.startsWith(
      BOARD_PATH() + "/" + BOARD_WRITE_PATH()
    );
    // 경로가 BOARD_PATH() + '/' + BOARD_WRITE_PATH()로 시작하면 게시판 작성 페이지임을 나타냅니다.
    setBoardWritePage(isBoardWritePage); // isBoardWritePage 상태를 업데이트합니다.

    const isBoardUpdatePage = pathname.startsWith(
      BOARD_PATH() + "/" + BOARD_UPDATE_PATH("")
    );
    // 경로가 BOARD_PATH() + '/' + BOARD_UPDATE_PATH('')로 시작하면 게시판 수정 페이지임을 나타냅니다.
    setBoardUpdatePage(isBoardUpdatePage); // isBoardUpdatePage 상태를 업데이트합니다.

    const isUserPage = pathname.startsWith(USER_PATH("")); // 경로가 USER_PATH('')로 시작하면 사용자 페이지임을 나타냅니다.
    setUserPage(isUserPage); // isUserPage 상태를 업데이트합니다.
  }, [pathname]); // pathname이 변경될 때마다 이 useEffect 훅이 실행됩니다.

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
          {(isAuthPage || isMainPage || isSearchPage || isBoardDetailPage) && (
            <SearchButton />
          )}
          {(isMainPage || isSearchPage || isBoardDetailPage || isUserPage) && (
            <MyPageButton />
          )}
          {(isBoardWritePage || isBoardUpdatePage) && <UploadButton />}
        </div>
      </div>
    </div>
  );
}
