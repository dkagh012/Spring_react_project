import React, { ChangeEvent, useEffect, useRef, useState } from "react";
import "./style.css";
import defaultProfileImage from "assets/image/default-profile-image.png";
import { useParams } from "react-router-dom";
export default function User() {
  // 마이페이지 여부 상태
  const { userEmail } = useParams();
  // 유저 화면 상단 컴포넌트
  const UserTop = () => {
    // 이미지 파일 인풋 참조 상태
    const imageInputRef = useRef<HTMLInputElement | null>(null);

    // 마이페이지 여부 상태
    const [isMyPage, setMyPage] = useState<boolean>(true);
    // 닉네임 변경 여부 상태
    const [isNicknameChange, setNicknameChange] = useState<boolean>(false);
    // 닉네임 상태
    const [nickname, setNickname] = useState<string>("");
    // 변경 닉네임 상태
    const [changeNickname, setChangeNickname] = useState<string>("");
    // 프로필 이미지 상태
    const [profileImage, setProfileImage] = useState<string | null>(null);

    // 프로필 박스 클릭 이벤트 처리
    const onProfileBoxClickHandler = () => {
      if (!isMyPage) return;
      if (!imageInputRef.current) return;
      imageInputRef.current.click();
    };

    // 닉네임 수정 버튼 클릭 이벤트 처리
    const onNicknameEditButtonClickHandler = () => {
      setChangeNickname(nickname);
      setNicknameChange(!isNicknameChange);
    };
    // 프로필 이미지 클릭 이벤트 처리
    const onProfileImageChangeHandler = (
      event: ChangeEvent<HTMLInputElement>
    ) => {
      if (!event.target.files || !event.target.files.length) return;

      const file = event.target.files[0];
      const data = new FormData();
      data.append("file", file);
    };

    // 닉네임 변경 이벤트 처리
    const onNicknameChangeHadler = (event: ChangeEvent<HTMLInputElement>) => {
      const { value } = event.target;
      setChangeNickname(value);
    };

    useEffect(() => {
      if (!userEmail) return;
      setNickname("나는주코야키");
      setProfileImage(
        "data:image/webp;base64,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"
      );
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
    return <div></div>;
  };

  return (
    <>
      <UserTop />
      <UserBottom />
    </>
  );
}
