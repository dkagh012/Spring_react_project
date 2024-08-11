import axios from "axios";
import { SignInRequestDto, SignUpRequestDto } from "./request/auth";
import { SignInResponseDto, SignUpResponseDto } from "./response/auth";
import { ResponseDto } from "./response";
import { GetSignInUserResponseDto } from "./response/user";
import { PostBoardRequestDto } from "./request/board";
import { PostBoardResponseDto } from "./response/board";

// API 요청을 보낼 서버의 기본 도메인 URL입니다.
const DOMAIN = "http://localhost:4000";

// API 요청을 보낼 기본 경로입니다.
const API_DOMAIN = `${DOMAIN}/api/v1`;

// 액세스 토큰을 사용하여 Authorization 헤더를 설정하는 함수입니다.
const authorization = (accessToken: string) => {
  return {
    headers: {
      Authorization: `Bearer ${accessToken}`, // Bearer 토큰으로 Authorization 헤더 설정
    },
  };
};

// 로그인 엔드포인트 URL을 생성하는 함수입니다.
const SIGN_IN_URL = () => `${API_DOMAIN}/auth/sign-in`;

// 회원가입 엔드포인트 URL을 생성하는 함수입니다.
const SIGN_UP_URL = () => `${API_DOMAIN}/auth/sign-up`;

// 로그인 요청을 처리하는 함수입니다.
export const signInRequest = async (requestBody: SignInRequestDto) => {
  const result = await axios
    .post(SIGN_IN_URL(), requestBody) // 로그인 엔드포인트로 POST 요청을 보냅니다.
    .then((response) => {
      const responseBody: SignInResponseDto = response.data; // 응답 데이터를 SignInResponseDto로 변환합니다.
      return responseBody;
    })
    .catch((error) => {
      if (!error.response.data) return null; // 오류 응답이 없는 경우 null을 반환합니다.
      const responseBody: ResponseDto = error.response.data; // 오류 응답 데이터를 ResponseDto로 변환합니다.
      return responseBody;
    });
  return result; // 최종 결과를 반환합니다.
};

// 회원가입 요청을 처리하는 함수입니다.
export const signUpRequest = async (requestBody: SignUpRequestDto) => {
  const result = await axios
    .post(SIGN_UP_URL(), requestBody) // 회원가입 엔드포인트로 POST 요청을 보냅니다.
    .then((response) => {
      const responseBody: SignUpResponseDto = response.data; // 응답 데이터를 SignUpResponseDto로 변환합니다.
      return responseBody;
    })
    .catch((error) => {
      if (!error.response.data) return null; // 오류 응답이 없는 경우 null을 반환합니다.
      const responseBody: ResponseDto = error.response.data; // 오류 응답 데이터를 ResponseDto로 변환합니다.
      return responseBody;
    });
  return result; // 최종 결과를 반환합니다.
};

// 게시물 생성 엔드포인트 URL을 생성하는 함수입니다.
const POST_BOARD_URL = () => `${API_DOMAIN}/board`;

// 게시물 생성 요청을 처리하는 함수입니다.
export const postBoardRequest = async (
  requestBody: PostBoardRequestDto,
  accessToken: string
) => {
  const result = await axios
    .post(POST_BOARD_URL(), requestBody, authorization(accessToken)) // 게시물 생성 엔드포인트로 POST 요청을 보냅니다.
    .then((response) => {
      const responseBody: PostBoardResponseDto = response.data; // 응답 데이터를 PostBoardResponseDto로 변환합니다.
      return responseBody;
    })
    .catch((error) => {
      if (!error.response) return null; // 오류 응답이 없는 경우 null을 반환합니다.
      const responseBody: ResponseDto = error.response.data; // 오류 응답 데이터를 ResponseDto로 변환합니다.
      return responseBody;
    });
  return result; // 최종 결과를 반환합니다.
};

// 현재 로그인된 사용자 정보를 가져오는 엔드포인트 URL을 생성하는 함수입니다.
const GET_SIGN_IN_USER_URL = () => `${API_DOMAIN}/user`;

// 현재 로그인된 사용자 정보를 요청하는 함수입니다.
export const getSignInUserRequest = async (accessToken: string) => {
  const result = await axios
    .get(GET_SIGN_IN_USER_URL(), authorization(accessToken)) // 사용자 정보를 가져오는 엔드포인트로 GET 요청을 보냅니다.
    .then((response) => {
      const responseBody: GetSignInUserResponseDto = response.data; // 응답 데이터를 GetSignInUserResponseDto로 변환합니다.
      return responseBody;
    })
    .catch((error) => {
      if (!error.response) return null; // 오류 응답이 없는 경우 null을 반환합니다.
      const responseBody: ResponseDto = error.response.data; // 오류 응답 데이터를 ResponseDto로 변환합니다.
      return responseBody;
    });
  return result; // 최종 결과를 반환합니다.
};

// 파일을 업로드할 서버의 도메인 URL입니다.
const FILE_DOMAIN = `${DOMAIN}/file`;

// 파일 업로드 엔드포인트 URL을 생성하는 함수입니다.
const FILE_UPLOAD_URL = () => `${FILE_DOMAIN}/upload`;

// 파일 업로드 시 사용하는 multipart/form-data 헤더를 설정합니다.
const mutipartFormData = { headers: { "Content-Type": "multipart/form-data" } };

// 파일 업로드 요청을 처리하는 함수입니다.
export const fileUploadRequest = async (data: FormData) => {
  const result = await axios
    .post(FILE_UPLOAD_URL(), data, mutipartFormData) // 파일 업로드 엔드포인트로 POST 요청을 보냅니다.
    .then((response) => {
      const responseBody: string = response.data; // 응답 데이터를 문자열로 변환합니다.
      return responseBody;
    })
    .catch((error) => {
      return null; // 오류가 발생하면 null을 반환합니다.
    });
  return result; // 최종 결과를 반환합니다.
};
