// PostBoardRequestDto 인터페이스를 정의하고, 이를 기본 내보내기(default export)로 설정합니다.
export default interface PostBoardRequestDto {
  // 게시물의 제목을 나타내는 필드입니다. 이 필드는 문자열이어야 합니다.
  title: string;

  // 게시물의 내용을 나타내는 필드입니다. 이 필드도 문자열이어야 합니다.
  content: string;

  // 게시물에 첨부된 이미지의 URL 목록을 나타내는 필드입니다.
  // 이 필드는 문자열 배열로 정의됩니다.
  boardImageList: string[];
}
