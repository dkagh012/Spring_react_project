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
import { fileUploadRequest, postBoardRequest } from "apis";
import { PostBoardRequestDto } from "apis/request/board";
import { PostBoardResponseDto } from "apis/response/board";
import { ResponseDto } from "apis/response";

// Header 컴포넌트 정의
export default function Header() {
  // 로그인 유저 상태를 가져오고, 관련 상태를 변경할 수 있는 함수들
  const { loginUser, setLoginUser, resetLoginUser } = useLoginUserStore();

  // 현재 경로(path)를 가져오는 hook
  const { pathname } = useLocation();

  // 쿠키 상태와 쿠키를 설정하는 함수
  const [cookie, setCookie] = useCookies();

  // 로그인 여부를 저장하는 상태
  const [isLogin, setLogin] = useState<boolean>(false);

  // 각각의 페이지 상태를 저장하는 상태 변수들
  const [isAuthPage, setAuthPage] = useState<boolean>(false);
  const [isMainPage, setMainPage] = useState<boolean>(false);
  const [isSearchPage, setSearchPage] = useState<boolean>(false);
  const [isBoardDetailPage, setBoardDetailPage] = useState<boolean>(false);
  const [isBoardWritePage, setBoardWritePage] = useState<boolean>(false);
  const [isBoardUpdatePage, setBoardUpdatePage] = useState<boolean>(false);
  const [isUserPage, setUserPage] = useState<boolean>(false);

  // 페이지 이동을 위한 네비게이트 함수
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

    // 마이페이지 버튼 클릭 핸들러
    const onMyPageButtonClickHandler = () => {
      if (!loginUser) return;
      const { email } = loginUser;
      navigate(USER_PATH(email));
    };

    // 로그아웃 버튼 클릭 핸들러
    const onSignOutButtonClickHandler = () => {
      resetLoginUser();
      setCookie("accessToken", "", { path: MAIN_PATH(), expires: new Date() });
      navigate(MAIN_PATH());
    };

    // 로그인 버튼 클릭 핸들러
    const onSignInButtonClickHandler = () => {
      navigate(AUTH_PATH());
    };

    // 마이페이지 버튼 컴포넌트 렌더링
    if (isLogin && userEmail === loginUser?.email)
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
    // 게시물 상태를 가져오고 초기화할 수 있는 함수들
    const { title, content, boardImageFileList, resetBoard } = useBoardStore();

    // 게시물 업로드 응답 처리 함수
    const postBoardResponse = (
      responseBody: PostBoardResponseDto | ResponseDto | null
    ) => {
      if (!responseBody) return;
      const { code } = responseBody;
      if (code === "DBE") alert("데이터베이스 오류입니다");
      if (code === "AF" || code === "NU") navigate(AUTH_PATH());
      if (code === "VF") alert("제목과 내용은 필수입니다.");
      if (code !== "SU") return;

      resetBoard(); // 게시물 상태 초기화
      if (!loginUser) return;
      const { email } = loginUser;
      navigate(USER_PATH(email)); // 유저 페이지로 네비게이트
    };

    // 업로드 버튼 클릭 이벤트 처리 함수
    const onUploadButtonClickHandler = async () => {
      const accessToken = cookie.accessToken;
      if (!accessToken) return;

      const boardImageList: string[] = [];

      // 각 이미지를 업로드하고 그 URL을 리스트에 추가
      for (const file of boardImageFileList) {
        const data = new FormData();
        data.append("file", file);

        const url = await fileUploadRequest(data);
        if (url) boardImageList.push(url);
      }

      // 게시물 생성 요청 본문을 작성
      const requestBody: PostBoardRequestDto = {
        title,
        content,
        boardImageList,
      };

      // 게시물 생성 요청을 보내고 응답을 처리
      postBoardRequest(requestBody, accessToken).then(postBoardResponse);
    };

    // 제목과 내용이 있으면 업로드 버튼 활성화
    if (title && content)
      return (
        <div className="black-button" onClick={onUploadButtonClickHandler}>
          {"업로드"}
        </div>
      );

    // 제목과 내용이 없으면 비활성화된 업로드 버튼 반환
    return <div className="disable-button">{"업로드"}</div>;
  };

  // path가 변경될 때마다 실행될 함수
  useEffect(() => {
    // 경로에 따라 각 페이지 상태를 설정
    const isAuthPage = pathname.startsWith(AUTH_PATH());
    setAuthPage(isAuthPage);

    const isMainPage = pathname === MAIN_PATH();
    setMainPage(isMainPage);

    const isSearchPage = pathname.startsWith(SEARCH_PATH(""));
    setSearchPage(isSearchPage);

    const isBoardDetailPage = pathname.startsWith(
      BOARD_PATH() + "/" + BOARD_DETAIL_PATH("")
    );
    setBoardDetailPage(isBoardDetailPage);

    const isBoardWritePage = pathname.startsWith(
      BOARD_PATH() + "/" + BOARD_WRITE_PATH()
    );
    setBoardWritePage(isBoardWritePage);

    const isBoardUpdatePage = pathname.startsWith(
      BOARD_PATH() + "/" + BOARD_UPDATE_PATH("")
    );
    setBoardUpdatePage(isBoardUpdatePage);

    const isUserPage = pathname.startsWith(USER_PATH(""));
    setUserPage(isUserPage);
  }, [pathname]);

  // loginUser가 변경될 때마다 실행될 함수
  useEffect(() => {
    setLogin(loginUser !== null);
  }, [loginUser]);

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
