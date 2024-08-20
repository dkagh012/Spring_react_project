import React from "react";
import "./style.css";
import { BoardListItem } from "types/interface";
import DefaultProfileImage from "assets/image/default-profile-image.png";
import { useNavigate } from "react-router-dom";
interface Props {
  top3ListItem: BoardListItem;
}

export default function Top3Item({ top3ListItem }: Props) {
  const { boardNumber, title, content, boardTitleImage } = top3ListItem;
  const { favoriteCount, commentCount, viewCount } = top3ListItem;
  const { writeDatetime, writerNickname, writerProfileImage } = top3ListItem;

  // const navigate = useNavigate();

  const onClickHandler = () => {
    // navigate(boardNumber)
  };

  return (
    <div
      className="top-3-list-item"
      style={{ backgroundImage: `url(${boardTitleImage})` }}
      onClick={onClickHandler}
    >
      <div className="top-3-list-item-main-box">
        <div className="top-3-list-item-top">
          <div className="top-3-list-item-profile-box">
            <div
              className="top-3-list-item-profile-image"
              style={{
                backgroundImage: `url(${
                  writerProfileImage ? writerProfileImage : DefaultProfileImage
                })`,
              }}
            ></div>
          </div>
          <div className="top-3-list-item-write-box">
            <div className="top-3-list-item-nickname">{writerNickname}</div>
            <div className="top-3-list-item-write-date">{writeDatetime}</div>
          </div>
        </div>
        <div className="top-3-list-item-middle">
          <div className="top-3-list-item-title">{title}</div>
          <div className="top-3-list-item-content">{content}</div>
        </div>
        <div className="top-3-list-item-bottom">
          <div className="top-3-list-item-counts">
            {`댓글 ${commentCount} ・ 좋아요 ${favoriteCount} ・ 조회수 ${viewCount}`}
          </div>
        </div>
      </div>
    </div>
  );
}
