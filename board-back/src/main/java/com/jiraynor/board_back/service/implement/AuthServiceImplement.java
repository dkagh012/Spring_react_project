package com.jiraynor.board_back.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jiraynor.board_back.DTO.request.auth.SignUpRequestDto;
import com.jiraynor.board_back.DTO.response.ResponseDto;
import com.jiraynor.board_back.DTO.response.auth.SignUpResponseDto;
import com.jiraynor.board_back.entity.UserEntity;
import com.jiraynor.board_back.repository.UserRepository;
import com.jiraynor.board_back.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {
    
    private final UserRepository userRepository;
    private  PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // PasswordEncoder를 final로 수정

    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) { // 여기서 메서드 이름과 서명 확인
        try {
            String email = dto.getEmail();
            boolean existedEmail = userRepository.existsByEmail(email);
            if (existedEmail) return SignUpResponseDto.duplicateEmail();

            String nickname = dto.getNickname();
            boolean existedNickname = userRepository.existsByNickname(nickname);
            if (existedNickname) return SignUpResponseDto.duplicateNickname();

            String telNumber = dto.getTelNumber();
            boolean existedTelNumber = userRepository.existsByTelNumber(telNumber);
            if (existedTelNumber) return SignUpResponseDto.duplicateTelNumber();

            String password = dto.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            dto.setPassword(encodedPassword);

            UserEntity userEntity = new UserEntity(dto);
            userRepository.save(userEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return SignUpResponseDto.success();
    }
}
