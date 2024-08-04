package com.jiraynor.board_back.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jiraynor.board_back.DTO.request.auth.SignInRequestDto;
import com.jiraynor.board_back.DTO.request.auth.SignUpRequestDto;
import com.jiraynor.board_back.DTO.response.ResponseDto;
import com.jiraynor.board_back.DTO.response.auth.SignUpResponseDto;
import com.jiraynor.board_back.DTO.response.auth.SignInResponseDto;
import com.jiraynor.board_back.entity.UserEntity;
import com.jiraynor.board_back.provider.JwtProvider;
import com.jiraynor.board_back.repository.UserRepository;
import com.jiraynor.board_back.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {
    
    private final UserRepository userRepository; // UserRepository 인스턴스를 주입받습니다.
    private final JwtProvider jwtProvider; // JwtProvider 인스턴스를 주입받습니다.

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // PasswordEncoder 인스턴스를 초기화합니다.

    // 회원가입 로직을 처리하는 메서드
    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {
        try {
            String email = dto.getEmail();
            // 이메일 중복 체크
            boolean existedEmail = userRepository.existsByEmail(email);
            if (existedEmail) return SignUpResponseDto.duplicateEmail();

            String nickname = dto.getNickname();
            // 닉네임 중복 체크
            boolean existedNickname = userRepository.existsByNickname(nickname);
            if (existedNickname) return SignUpResponseDto.duplicateNickname();

            String telNumber = dto.getTelNumber();
            // 전화번호 중복 체크
            boolean existedTelNumber = userRepository.existsByTelNumber(telNumber);
            if (existedTelNumber) return SignUpResponseDto.duplicateTelNumber();

            String password = dto.getPassword();
            // 비밀번호 암호화
            String encodedPassword = passwordEncoder.encode(password);
            dto.setPassword(encodedPassword);

            // UserEntity 생성 및 저장
            UserEntity userEntity = new UserEntity(dto);
            userRepository.save(userEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError(); // 데이터베이스 오류 발생 시 반환
        }
        return SignUpResponseDto.success(); // 성공적으로 회원가입 처리 완료 시 반환
    }

    // 로그인 로직을 처리하는 메서드
    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {
        
        String token = null;

        try {
            String email = dto.getEmail();
            // 이메일로 사용자 정보 조회
            UserEntity userEntity = userRepository.findByEmail(email);
            if (userEntity == null) return SignInResponseDto.signInFail(); // 사용자가 존재하지 않을 경우 반환

            String password = dto.getPassword();
            String encodePassword = userEntity.getPassword();
            // 비밀번호 매칭 체크
            boolean isMatched = passwordEncoder.matches(password, encodePassword);
            if (!isMatched) return SignInResponseDto.signInFail(); // 비밀번호가 일치하지 않을 경우 반환

            // JWT 토큰 생성
            token = jwtProvider.create(email);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError(); // 데이터베이스 오류 발생 시 반환
        }
        return SignInResponseDto.success(token); // 성공적으로 로그인 처리 완료 시 반환
    }
}
