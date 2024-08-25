package com.jiraynor.board_back.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jiraynor.board_back.service.UserService;
import com.jiraynor.board_back.DTO.request.user.PatchNicknameRequestDto;
import com.jiraynor.board_back.DTO.request.user.PatchProfileImageRequestDto;
import com.jiraynor.board_back.DTO.response.ResponseDto;
import com.jiraynor.board_back.DTO.response.user.GetSignInUserResponseDto;
import com.jiraynor.board_back.DTO.response.user.GetUserResponseDto;
import com.jiraynor.board_back.DTO.response.user.PatchNicknameResponseDto;
import com.jiraynor.board_back.DTO.response.user.PatchProfileImageResponseDto;
import com.jiraynor.board_back.entity.UserEntity;
import com.jiraynor.board_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service // 이 클래스가 서비스 계층의 컴포넌트임을 나타내며, Spring에 의해 관리됩니다.
@RequiredArgsConstructor // final 필드에 대한 생성자를 Lombok을 통해 자동으로 생성해줍니다.
public class UserServiceImplement implements UserService {

    // UserRepository를 사용하기 위해 의존성 주입을 통해 인스턴스를 가져옵니다.
    private final UserRepository userRepository;


    @Override
    public ResponseEntity<? super GetUserResponseDto> getUser(String email) {

        UserEntity userEntity = null;

        try{

            userEntity = userRepository.findByEmail(email);
            if(userEntity == null) return GetUserResponseDto.noExistUser();

        }catch(Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetUserResponseDto.success(userEntity);

    }


    @Override
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email) {
        UserEntity userEntity = null;
        try {
            // 주어진 이메일로 데이터베이스에서 사용자 정보를 조회합니다.
            userEntity = userRepository.findByEmail(email);
            
            // 사용자가 존재하지 않는 경우, 적절한 응답을 반환합니다.
            if (userEntity == null) return GetSignInUserResponseDto.notExistUser();
        } catch (Exception exception) {
            // 데이터베이스 오류가 발생한 경우, 오류 메시지를 포함한 응답을 반환합니다.
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        // 사용자 정보 조회에 성공한 경우, 성공 응답을 반환합니다.
        return GetSignInUserResponseDto.success(userEntity);
    }

    @Override
    public ResponseEntity<? super PatchNicknameResponseDto> patchNickname(PatchNicknameRequestDto dto, String email) {
        try {
            UserEntity userEntity = userRepository.findByEmail(email);
            if (userEntity == null) return PatchNicknameResponseDto.noExistUser();
    
            String nickname = dto.getNickname();
            boolean existedNickname = userRepository.existsByNickname(nickname);
            if (existedNickname) return PatchNicknameResponseDto.duplicateNickname();
    
            userEntity.setNickname(nickname);
            userRepository.save(userEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
    
        return PatchNicknameResponseDto.success();
    }
    

    @Override
    public ResponseEntity<? super PatchProfileImageResponseDto> patchProfileImage(PatchProfileImageRequestDto dto, String email) {
        try {
            UserEntity userEntity = userRepository.findByEmail(email);
            if (userEntity == null) return PatchProfileImageResponseDto.noExistUser();
    
            String profileImage = dto.getProfileImage();
            userEntity.setProfileImage(profileImage);
            userRepository.save(userEntity);
    
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
    
        return PatchProfileImageResponseDto.success();
    }
    
}
