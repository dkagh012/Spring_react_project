package com.jiraynor.board_back.common;

// ResponseCode 인터페이스는 애플리케이션 전반에서 사용할 응답 코드를 상수로 정의합니다.
public interface ResponseCode {

    // HTTP Status 200: 요청이 성공적으로 처리되었음을 나타냄
    String SUCCESS = "SU";

    // HTTP Status 400: 클라이언트의 잘못된 요청을 나타냄
    String VALIDATION_FAILED = "VE"; // 유효성 검증 실패
    String DUPLICATE_EMAIL = "DE"; // 중복된 이메일
    String DUPLICATE_NICKNAME = "DN"; // 중복된 닉네임
    String DUPLICATE_TEL_NUMBER = "DT"; // 중복된 전화번호
    String NOT_EXISTED_USER = "NU"; // 존재하지 않는 사용자
    String NOT_EXISTED_BOARD = "NB"; // 존재하지 않는 게시물

    // HTTP Status 401: 인증 실패를 나타냄
    String SIGN_IN_FAIL = "SF"; // 로그인 실패
    String AUTHORIZATION_FAIL = "AF"; // 권한 인증 실패

    // HTTP Status 403: 권한 없음(Forbidden)을 나타냄
    String NO_PERMISSION = "NP"; // 권한 없음

    // HTTP Status 500: 서버 내부 오류를 나타냄
    String DATABASE_ERROR = "DBE"; // 데이터베이스 오류
}
