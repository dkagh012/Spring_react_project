package com.jiraynor.board_back.common;

// ResponseMessage 인터페이스는 애플리케이션 전반에서 사용할 응답 메시지를 상수로 정의합니다.
public interface ResponseMessage {

    // HTTP Status 200: 요청이 성공적으로 처리되었음을 나타냄
    String SUCCESS = "Success.";

    // HTTP Status 400: 클라이언트의 잘못된 요청을 나타냄
    String VALIDATION_FAILED = "Validation failed."; // 유효성 검증 실패
    String DUPLICATE_EMAIL = "Duplicate email."; // 중복된 이메일
    String DUPLICATE_NICKNAME = "Duplicate nickname."; // 중복된 닉네임
    String DUPLICATE_TEL_NUMBER = "Duplicate tel number."; // 중복된 전화번호
    String NOT_EXISTED_USER = "This user does not exist."; // 존재하지 않는 사용자
    String NOT_EXISTED_BOARD = "This board does not exist."; // 존재하지 않는 게시물

    // HTTP Status 401: 인증 실패를 나타냄
    String SIGN_IN_FAIL = "Login information mismatch."; // 로그인 정보 불일치
    String AUTHORIZATION_FAIL = "Authorization failed."; // 권한 인증 실패

    // HTTP Status 403: 권한 없음(Forbidden)을 나타냄
    String NO_PERMISSION = "Do not have permission."; // 권한 없음

    // HTTP Status 500: 서버 내부 오류를 나타냄
    String DATABASE_ERROR = "Database error."; // 데이터베이스 오류
}
