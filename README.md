1. common

   ResponseCode.java
   애플리케이션 전반에서 사용되는 응답 코드를 정의합니다. 예를 들어, 성공(200), 잘못된 요청(400), 서버 오류(500) 등의 상태 코드를 상수로 정의하여 코드의 가독성과 유지보수를 쉽게 합니다.

   ResponseMessage.java
   응답 코드에 대응하는 메시지를 정의합니다. 예를 들어, 성공 시 "요청이 성공적으로 처리되었습니다."와 같은 메시지를 상수로 정의하여 일관된 응답 메시지를 제공합니다.

2. config

   CorsConfig.java
   CORS(Cross-Origin Resource Sharing) 설정을 담당합니다. 다른 도메인에서의 리소스 요청을 허용하거나 차단하는 설정을 정의합니다. 예를 들어, 특정 도메인에서만 API를 호출할 수 있도록 설정할 수 있습니다.

   WebSecurityConfig.java
   Spring Security 설정을 담당합니다. 사용자 인증 및 권한 부여, 보안 필터 체인, 비밀번호 암호화 등의 보안 관련 설정을 정의합니다. 예를 들어, 특정 URL 패턴에 대해 인증을 요구하거나 접근 권한을 설정합니다.

3. controller

   AuthController.java
   인증 관련 API 엔드포인트를 제공하는 컨트롤러입니다. 로그인, 회원가입, 토큰 갱신 등의 요청을 처리합니다. 예를 들어, /auth/signin 엔드포인트에서 로그인 요청을 받아 사용자 인증을 처리합니다.

4. DTO (Data Transfer Object)
   object

   BoardListItem.java
   게시판 목록의 각 아이템을 표현하는 DTO입니다. 게시글의 제목, 작성자, 작성일 등의 정보를 담습니다.

   CommentListItem.java
   댓글 목록의 각 아이템을 표현하는 DTO입니다. 댓글 내용, 작성자, 작성일 등의 정보를 담습니다.

   FavoriteListItem.java
   즐겨찾기 목록의 각 아이템을 표현하는 DTO입니다. 즐겨찾기된 항목의 제목, URL 등의 정보를 담습니다.

   request
   auth
   SignInRequestDto.java
   로그인 요청 시 사용되는 DTO입니다. 사용자 이메일과 비밀번호 등의 로그인 정보를 담습니다.

   SignUpRequestDto.java
   회원가입 요청 시 사용되는 DTO입니다. 사용자 이메일, 비밀번호, 이름 등의 회원가입 정보를 담습니다.

   response
   auth
   ResponseDto.java
   인증 관련 응답 DTO입니다. 예를 들어, 로그인 성공 시 JWT 토큰과 사용자 정보를 담아서 클라이언트에 반환합니다.

5. entity
   primaryKey
   FavoritePk.java
   복합 기본 키를 정의하는 클래스입니다. 예를 들어, 사용자 ID와 게시글 ID를 조합하여 즐겨찾기의 기본 키로 사용합니다.

   BoardEntity.java
   게시판 게시글을 표현하는 엔티티입니다. 게시글의 제목, 내용, 작성자, 작성일 등의 속성을 갖습니다.

   BoardListViewEntity.java
   게시판 목록 뷰를 표현하는 엔티티입니다. 게시글의 목록을 조회할 때 필요한 최소한의 정보를 갖습니다.

   CommentEntity.java
   댓글을 표현하는 엔티티입니다. 댓글 내용, 작성자, 작성일 등의 속성을 갖습니다.

   FavoriteEntity.java
   즐겨찾기를 표현하는 엔티티입니다. 사용자가 즐겨찾기한 게시글 정보를 갖습니다.

   ImageEntity.java
   이미지를 표현하는 엔티티입니다. 이미지 파일 경로, 업로드된 시간 등의 속성을 갖습니다.

   SearchLogEntity.java
   검색 로그를 표현하는 엔티티입니다. 사용자가 검색한 키워드, 검색 시간 등의 속성을 갖습니다.

   UserEntity.java
   사용자를 표현하는 엔티티입니다. 사용자 이메일, 비밀번호, 이름 등의 속성을 갖습니다.

6. filter
   JwtAuthenticationFilter.java
   JWT 인증 필터입니다. 클라이언트 요청에 포함된 JWT 토큰을 검증하고, 유효한 토큰일 경우 사용자 정보를 컨텍스트에 설정합니다. 이를 통해 인증된 사용자인지 확인하고, 보호된 리소스에 접근할 수 있도록 합니다.

7. provider
   JwtProvider.java
   JWT 토큰을 생성하고 검증하는 로직을 포함하는 클래스입니다. 사용자의 로그인 요청이 성공하면 JWT 토큰을 생성하고, 이후 요청에서 토큰의 유효성을 검증하여 인증을 처리합니다.

8. repository
   BoardListViewRepository.java
   게시판 목록 뷰에 접근하기 위한 레포지토리 인터페이스입니다. Spring Data JPA를 사용하여 DB에서 게시판 목록을 조회하는 메서드를 제공합니다.

   BoardRepository.java
   게시판 게시글에 접근하기 위한 레포지토리 인터페이스입니다. CRUD(Create, Read, Update, Delete) 작업을 수행하는 메서드를 제공합니다.

   CommentRepository.java
   댓글에 접근하기 위한 레포지토리 인터페이스입니다. 댓글 CRUD 작업을 수행하는 메서드를 제공합니다.

   FavoriteRepository.java
   즐겨찾기에 접근하기 위한 레포지토리 인터페이스입니다. 즐겨찾기 CRUD 작업을 수행하는 메서드를 제공합니다.

   ImageRepository.java
   이미지에 접근하기 위한 레포지토리 인터페이스입니다. 이미지 CRUD 작업을 수행하는 메서드를 제공합니다.

   SearchLogRepository.java
   검색 로그에 접근하기 위한 레포지토리 인터페이스입니다. 검색 로그 CRUD 작업을 수행하는 메서드를 제공합니다.

   UserRepository.java
   사용자에 접근하기 위한 레포지토리 인터페이스입니다. 사용자 CRUD 작업을 수행하는 메서드를 제공합니다.

9. service
   implement

   AuthService.java
   인증 서비스를 구현하는 클래스입니다. 사용자 로그인, 회원가입, JWT 토큰 검증 등의 로직을 포함합니다. 예를 들어, 사용자가 로그인을 시도하면 이메일과 비밀번호를 검증하고, 성공 시 JWT 토큰을 생성하여 반환합니다.

   BoardBackApplication.java
   Spring Boot 애플리케이션의 메인 클래스입니다. 애플리케이션을 실행하기 위한 엔트리 포인트를 제공합니다. @SpringBootApplication 애노테이션을 사용하여 스프링 부트 설정을 자동으로 구성합니다.
