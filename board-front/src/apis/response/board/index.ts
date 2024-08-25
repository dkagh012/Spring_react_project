// "post-board.response.dto" 파일에서 PostBoardResponseDto를 가져옵니다.
import PostBoardResponseDto from "./post-board.response.dto";
import GetBoardResponseDto from "./get-board.response.dto";
import IncreaseViewCountResponseDto from "./increase-view-count.response.dto";
import GetFavoriteListResponseDto from "./get-favorite-list.response.dto";
import GetCommentListResponseDto from "./get-comment-list.response.dto";
import GetTop3BoardListResponseDto from "./get-top-3-board-list.response.dto";
import GetLatestBoardListResponseDto from "./get-latest-board-list.response.dto";
import PutFavoriteResponseDto from "./put-favorite.response.dto";
import PostCommentResponseDto from "./post-comment.response.dto";
import PatchBoardResponseDto from "./patch-board.response.dto";
import DeleteBoardResponseDto from "./delete-board.response.dto";
// PostBoardResponseDto 타입을 다른 파일에서 사용할 수 있도록 내보냅니다.
export type {
  GetCommentListResponseDto,
  GetFavoriteListResponseDto,
  GetBoardResponseDto,
  GetTop3BoardListResponseDto,
  GetLatestBoardListResponseDto,
  PostBoardResponseDto,
  PostCommentResponseDto,
  DeleteBoardResponseDto,
  IncreaseViewCountResponseDto,
  PutFavoriteResponseDto,
  PatchBoardResponseDto,
};
