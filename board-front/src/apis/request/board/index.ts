// "post-board.request.dto" 파일에서 PostBoardRequestDto를 가져옵니다.
import PostBoardRequestDto from "./post-board.request.dto";
import PostCommentRequestDto from "./post-comment.request.dto";
import PatchBoardRequestDto from "./patch-board.request.dto";
// PostBoardRequestDto 타입을 다른 파일에서 사용할 수 있도록 내보냅니다.
export type {
  PostBoardRequestDto,
  PostCommentRequestDto,
  PatchBoardRequestDto,
};
