import React, { ChangeEvent, useEffect, useRef, useState } from "react";
import "./style.css";
import defaultProfileImage from "assets/image/default-profile-image.png";
import { useNavigate, useParams } from "react-router-dom";
import { BoardListItem } from "types/interface";
import { usePagination } from "hooks";
import { latestBoardListMock } from "mocks";
import BoardItem from "components/BoardItem";
import { useLoginUserStore } from "stores";
import { BOARD_PATH, BOARD_WRITE_PATH, MAIN_PATH, USER_PATH } from "constant";
import {
  fileUploadRequest,
  getUserBOardListRequest,
  getUserRequest,
  patchNicknameRequest,
  patchProfileImageRequest,
} from "apis";
import {
  GetUserResponseDto,
  PatchNicknameResponseDto,
  PatchProfileImageResponseDto,
} from "apis/response/user";
import { ResponseDto } from "apis/response";
import { useCookies } from "react-cookie";
import {
  PatchNicknameRequestDto,
  PatchProfileImageRequestDto,
} from "apis/request/user";
import { GetUserBoardListResponseDto } from "apis/response/board";
import Pagination from "components/Pagination";
export default function User() {
  // userEmail path 상태
  const { userEmail } = useParams();
  // 로그인 유저 상태
  const { loginUser } = useLoginUserStore();
  // 쿠키 상태
  const [cookies, setCookies] = useCookies();
  // 마이페이지 여부 상태
  const [isMyPage, setMyPage] = useState<boolean>(true);
  // 네비게이트 함수
  const navigate = useNavigate();
  // 유저 화면 상단 컴포넌트
  const UserTop = () => {
    // 이미지 파일 인풋 참조 상태
    const imageInputRef = useRef<HTMLInputElement | null>(null);
    // 닉네임 변경 여부 상태
    const [isNicknameChange, setNicknameChange] = useState<boolean>(false);
    // 닉네임 상태
    const [nickname, setNickname] = useState<string>("");
    // 변경 닉네임 상태
    const [changeNickname, setChangeNickname] = useState<string>("");
    // 프로필 이미지 상태
    const [profileImage, setProfileImage] = useState<string | null>(null);

    const getUserResponse = (
      responseBody: GetUserResponseDto | ResponseDto | null
    ) => {
      if (!responseBody) return;
      const { code } = responseBody;
      if (code === "NU") alert("존재하지 않는 유저 입니다.");
      if (code === "DBE") alert("데이터베이스 오류입니다.");
      if (code !== "SU") {
        navigate(MAIN_PATH());
        return;
      }
      const { email, nickname, profileImage } =
        responseBody as GetUserResponseDto;
      setNickname(nickname);
      setProfileImage(profileImage);
      const isMyPage = email === loginUser?.email;
      setMyPage(isMyPage);
    };

    const fileUploadResponse = (profileImage: string | null) => {
      if (!profileImage) return;
      if (!cookies.accessToken) return;

      const requestBody: PatchProfileImageRequestDto = { profileImage };
      patchProfileImageRequest(requestBody, cookies.accessToken).then(
        patchProfileImageResponse
      );
    };

    const patchProfileImageResponse = (
      responseBody: PatchProfileImageResponseDto | ResponseDto | null
    ) => {
      if (!responseBody) return;
      const { code } = responseBody;
      if (code === "AF") alert("인증에 실패했습니다.");
      if (code === "NU") alert("존재하지 않는 유저 입니다.");
      if (code === "DBE") alert("데이터베이스 오류입니다.");
      if (code !== "SU") return;

      if (!userEmail) return;
      getUserRequest(userEmail).then(getUserResponse);
    };

    const patchNicknameResponse = (
      responseBody: PatchNicknameResponseDto | ResponseDto | null
    ) => {
      if (!responseBody) return;
      const { code } = responseBody;
      if (code === "VF") alert("닉네임은 필수입니다.");
      if (code === "AF") alert("인증에 실패했습니다.");
      if (code === "DN") alert("중복되는 닉네임입니다.");
      if (code === "NP") alert("권한이 없습니다.");
      if (code === "NU") alert("존재하지 않는 유저 입니다.");
      if (code === "DBE") alert("데이터베이스 오류입니다.");
      if (code !== "SU") return;

      if (!userEmail) return;
      getUserRequest(userEmail).then(getUserResponse);
      setNicknameChange(false);
    };
    // 프로필 박스 클릭 이벤트 처리
    const onProfileBoxClickHandler = () => {
      if (!isMyPage) return;
      if (!imageInputRef.current) return;
      imageInputRef.current.click();
    };

    // 닉네임 수정 버튼 클릭 이벤트 처리
    const onNicknameEditButtonClickHandler = () => {
      if (!isNicknameChange) {
        setChangeNickname(nickname);
        setNicknameChange(!isNicknameChange);
        return;
      }
      if (!cookies.accessToken) return;
      const requestBody: PatchNicknameRequestDto = {
        nickname: changeNickname,
      };
      patchNicknameRequest(requestBody, cookies.accessToken).then(
        patchNicknameResponse
      );
    };
    // 프로필 이미지 클릭 이벤트 처리
    const onProfileImageChangeHandler = (
      event: ChangeEvent<HTMLInputElement>
    ) => {
      if (!event.target.files || !event.target.files.length) return;

      const file = event.target.files[0];
      const data = new FormData();
      data.append("file", file);

      fileUploadRequest(data).then(fileUploadResponse);
    };

    // 닉네임 변경 이벤트 처리
    const onNicknameChangeHadler = (event: ChangeEvent<HTMLInputElement>) => {
      const { value } = event.target;
      setChangeNickname(value);
    };

    useEffect(() => {
      if (!userEmail) return;
      getUserRequest(userEmail).then(getUserResponse);
    }, [userEmail]);

    return (
      <div id="user-top-wrapper">
        <div className="user-top-container">
          {isMyPage ? (
            <div
              className="user-top-my-profile-image-box"
              onClick={onProfileBoxClickHandler}
            >
              {profileImage !== null ? (
                <div
                  className="user-top-profile-image"
                  style={{
                    backgroundImage: `url(${profileImage})`,
                  }}
                ></div>
              ) : (
                <div className="icon-box-large">
                  <div className="icon image-box-white-icon"></div>
                </div>
              )}
              <input
                ref={imageInputRef}
                type="file"
                accept="image/*"
                style={{ display: "none" }}
                onChange={onProfileImageChangeHandler}
              />
            </div>
          ) : (
            <div
              className="user-top-profile-image-box"
              style={{
                backgroundImage: `url(${
                  profileImage ? profileImage : defaultProfileImage
                })`,
              }}
            ></div>
          )}
          <div className="user-top-info-box">
            <div className="user-top-info-nickname-box">
              {isMyPage ? (
                <>
                  {isNicknameChange ? (
                    <input
                      className="user-top-info-nickname-input"
                      type="text"
                      size={changeNickname.length + 2}
                      value={changeNickname}
                      onChange={onNicknameChangeHadler}
                    />
                  ) : (
                    <div className="user-top-info-nickname">{nickname}</div>
                  )}
                  <div
                    className="icon-button"
                    onClick={onNicknameEditButtonClickHandler}
                  >
                    <div className="icon edit-icon"></div>
                  </div>
                </>
              ) : (
                <>
                  <div className="user-top-info-nickname">{nickname}</div>
                </>
              )}
            </div>
            <div className="user-top-info-email">{"email@email.com"}</div>
          </div>
        </div>
      </div>
    );
  };

  // 유저 화면 하단 컴포넌트
  const UserBottom = () => {
    const {
      currentPage,
      currentSection,
      viewList,
      viewPageList,
      totalSection,
      setCurrentPage,
      setCurrentSection,
      setTotalList,
    } = usePagination<BoardListItem>(5);
    // state: 게시물 개수 상태
    const [count, setCount] = useState<number>(2);

    const getUserBoardListResponse = (
      responseBody: GetUserBoardListResponseDto | ResponseDto | null
    ) => {
      if (!responseBody) return;
      const { code } = responseBody;
      if (code === "NU") {
        alert("존재하지 않는 유저입니다.");
        navigate(MAIN_PATH());
        return;
      }
      if (code === "DBE") alert("데이터베이스 오류입니다.");
      if (code !== "SU") return;

      const { userBoardList } = responseBody as GetUserBoardListResponseDto;
      setTotalList(userBoardList);
      setCount(userBoardList.length);
    };

    // 사이드 카드 클릭 이벤트 처리
    const onSideCardClickHandler = () => {
      if (isMyPage) navigate(BOARD_PATH() + "/" + BOARD_WRITE_PATH());
      else if (loginUser) navigate(USER_PATH(loginUser.email));
    };

    useEffect(() => {
      if (!userEmail) return;
      getUserBOardListRequest(userEmail).then(getUserBoardListResponse);
    }, [userEmail]);
    // render: 유저 화면 하단 컴포넌트 렌더링
    return (
      <div id="user-bottom-wrapper">
        <div className="user-bottom-container">
          <div className="user-bottom-title">
            {isMyPage ? "내 게시물" : "게시물"}
            <span className="emphasis">{count}</span>
          </div>
          <div className="user-bottom-contents-box">
            {count === 0 ? (
              <div className="user-bottom-contents-nothing">
                게시물이 없습니다.
              </div>
            ) : (
              <div className="user-bottom-contents">
                {viewList.map((boardListItem) => (
                  <BoardItem boardListItem={boardListItem} />
                ))}
              </div>
            )}
            <div className="user-bottom-side-box">
              <div
                className="user-bottom-side-card"
                onClick={onSideCardClickHandler}
              >
                <div className="user-bottom-side-container">
                  {isMyPage ? (
                    <>
                      <div className="icon-box">
                        <div className="icon edit-icon"></div>
                      </div>
                      <div className="user-bottom-side-text">{"글쓰기"}</div>
                    </>
                  ) : (
                    <>
                      <div className="user-bottom-side-text">
                        {"내 게시물로 가기"}
                      </div>
                      <div className="icon-box">
                        <div className="icon arrow-right-icon"></div>
                      </div>
                    </>
                  )}
                </div>
              </div>
            </div>
          </div>
          <div className="user-bottom-pagination-box">
            {count !== 0 && (
              <Pagination
                currentPage={currentPage}
                currentSection={currentSection}
                SetCurrentPage={setCurrentPage}
                SetCurrentSection={setCurrentSection}
                viewPageList={viewPageList}
                totalSection={totalSection}
              />
            )}
          </div>
        </div>
      </div>
    );
  };

  return (
    <>
      <UserTop />
      <UserBottom />
    </>
  );
}
