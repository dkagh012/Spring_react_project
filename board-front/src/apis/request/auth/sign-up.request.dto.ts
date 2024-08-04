export default interface SignUpRequestDto {
  email: string; // 이메일 주소를 나타내는 문자열
  password: string; // 비밀번호를 나타내는 문자열
  nickname: string; // 닉네임을 나타내는 문자열
  telNumber: string; // 전화번호를 나타내는 문자열
  address: string; // 주소를 나타내는 문자열
  addressDetail: string | null; // 상세 주소를 나타내는 문자열 또는 null
  agreedPersonal: boolean; // 개인정보 동의 여부를 나타내는 불리언 값
}
