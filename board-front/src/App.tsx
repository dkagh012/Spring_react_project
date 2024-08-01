import React from "react";
import "./App.css";
import BoardListItem from "components/BoardItem";
import {
  commentListMock,
  favoriteListMock,
  latestBoardListMock,
  top3BoardListMock,
} from "mocks";
import Top3Item from "components/Top3Item";
import CommentItem from "components/CommentItem";
import FavoriteItem from "components/FavoriteItem";

function App() {
  return (
    <>
      {/* {latestBoardListMock.map((boardListItem) => (
        <BoardListItem boardListItem={boardListItem} />
      ))} */}

      {/* <div style={{ display: "flex", justifyContent: "center", gap: "24px" }}>
        {top3BoardListMock.map((top3ListItem) => (
          <Top3Item top3ListItem={top3ListItem} />
        ))}
      </div> */}
      {/* <div
        style={{
          padding: "0 20px",
          display: "flex",
          flexDirection: "column",
          gap: "30px",
        }}
      >
        {commentListMock.map((commentListItem) => (
          <CommentItem commentListItem={commentListItem} />
        ))} */}
      <div
        style={{
          display: "flex",
          columnGap: "column",
          rowGap: "30px",
        }}
      >
        {favoriteListMock.map((favoriteListItem) => (
          <FavoriteItem favoriteListItem={favoriteListItem} />
        ))}
      </div>
    </>
  );
}

export default App;
