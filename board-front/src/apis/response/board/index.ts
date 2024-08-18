// "post-board.response.dto" 파일에서 PostBoardResponseDto를 가져옵니다.
import PostBoardResponseDto from "./post-board.response.dto";
import GetBoardResponseDto from "./get-board.response.dto";
import IncreaseViewCountResponseDto from "./increase-view-count.response.dto";
import GetFavoriteListResponseDto from "./get-favorite-list.response.dto";
import GetCommentListResponseDto from "./get-comment-list.response.dto";
// PostBoardResponseDto 타입을 다른 파일에서 사용할 수 있도록 내보냅니다.
export type {
  PostBoardResponseDto,
  GetCommentListResponseDto,
  GetFavoriteListResponseDto,
  GetBoardResponseDto,
  IncreaseViewCountResponseDto,
};
