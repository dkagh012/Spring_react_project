import React, { useEffect, useRef, useState } from "react";
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

    useEffect(() => {
      if (!userEmail) return;
      setNickname("나는주코야키");
      setProfileImage(
        "https://cdn.eyesmag.com/content/uploads/sliderImages/2023/01/03/tiffany-and-co-tiffanylock-blackpink-rose-2-44b7a1ad-728b-4e7c-8da7-13e6767b0d8a.jpg"
      );
    }, [userEmail]);

    return (
      <div id="user-top-wrapper">
        <div className="user-top-container">
          {isMyPage ? (
            <div className="user-top-my-profile-image-box">
              {profileImage !== null ? (
                <div
                  className="user-top-profile-image"
                  style={{
                    backgroundImage: `url(${profileImage})`,
                  }}
                ></div>
              ) : (
                <div className="user-top-my-profile-image-nothing-box">
                  <div className="icon-box-large">
                    <div className="icon image-box-white-icon"></div>
                  </div>
                </div>
              )}
              <input
                ref={imageInputRef}
                type="file"
                accept="image/*"
                style={{ display: "none" }}
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
                      size={changeNickname.length + 1}
                      value={changeNickname}
                    />
                  ) : (
                    <div className="user-top-info-nickname">{nickname}</div>
                  )}
                  <div className="icon-button">
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
