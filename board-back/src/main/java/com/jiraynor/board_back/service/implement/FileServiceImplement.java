package com.jiraynor.board_back.service.implement;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jiraynor.board_back.service.FileService;

@Service
public class FileServiceImplement implements FileService {

    // application.properties 또는 application.yml 파일에서 설정된 파일 경로를 주입받습니다.
    @Value("${file.path}")
    private String filePath;

    // application.properties 또는 application.yml 파일에서 설정된 파일 URL을 주입받습니다.
    @Value("${file.url}")
    private String fileUrl;

    // 파일을 업로드하는 메서드입니다.
    @Override
    public String upload(MultipartFile file) {

        // 파일이 비어 있으면 null을 반환하여 업로드 실패를 나타냅니다.
        if (file.isEmpty()) return null;
        
        // 파일의 원래 이름을 가져옵니다.
        String originalFileName = file.getOriginalFilename();
        
        // 파일 확장자를 추출합니다.
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        
        // UUID를 생성하여 파일 이름에 추가합니다.
        String uuid = UUID.randomUUID().toString();
        
        // 최종 저장될 파일 이름을 생성합니다.
        String saveFileName = uuid + extension;
        
        // 파일이 저장될 경로를 생성합니다.
        String savePath = filePath + saveFileName;

        try {
            // 파일을 지정된 경로에 저장합니다.
            file.transferTo(new File(savePath));
        } catch (Exception exception) {
            // 파일 저장 중 예외가 발생하면 예외 메시지를 출력하고 null을 반환합니다.
            exception.printStackTrace();
            return null;
        }

        // 저장된 파일의 URL을 반환합니다.
        String url = fileUrl + saveFileName;
        return url;
    }

    // 파일 이름을 기반으로 이미지를 가져오는 메서드입니다.
    @Override
    public Resource getImage(String fileName) {
        Resource resource = null;

        try {
            // 지정된 파일 경로에서 리소스를 로드합니다.
            resource = new UrlResource("file:" + filePath + fileName);
        } catch (Exception exception) {
            // 리소스 로드 중 예외가 발생하면 예외 메시지를 출력하고 null을 반환합니다.
            exception.printStackTrace();
            return null;
        }
        // 로드된 리소스를 반환합니다.
        return resource;
    }
}
