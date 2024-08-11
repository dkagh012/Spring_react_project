package com.jiraynor.board_back.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

// 파일 업로드 및 이미지 조회 기능을 정의하는 인터페이스입니다.
public interface FileService {

    // 파일을 업로드하고, 저장된 파일의 URL을 반환하는 메서드입니다.
    // MultipartFile 객체를 매개변수로 받아 처리합니다.
    String upload(MultipartFile file);

    // 파일 이름을 기반으로 이미지를 가져오는 메서드입니다.
    // Resource 객체를 반환하여 이미지 파일에 대한 접근을 제공합니다.
    Resource getImage(String fileName);

}
