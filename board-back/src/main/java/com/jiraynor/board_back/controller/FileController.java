package com.jiraynor.board_back.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jiraynor.board_back.service.FileService;

import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    // 파일 업로드 및 이미지를 반환하는 서비스 클래스에 대한 의존성을 주입받습니다.
    private final FileService fileService;

    // 파일을 업로드하는 엔드포인트입니다. 
    // @PostMapping 어노테이션을 사용하여 HTTP POST 요청을 처리하며, "/upload" 경로에 매핑됩니다.
    @PostMapping("/upload")
    public String upload(
        @RequestParam("file") MultipartFile file  // 클라이언트가 업로드한 파일을 매개변수로 받습니다.
    ) {
        // FileService의 upload 메소드를 호출하여 파일을 저장하고, 저장된 파일의 URL을 반환합니다.
        String url = fileService.upload(file);
        return url;  // 업로드된 파일의 URL을 클라이언트에게 반환합니다.
    }

    // 업로드된 이미지를 가져오는 엔드포인트입니다.
    // @GetMapping 어노테이션을 사용하여 HTTP GET 요청을 처리하며, 경로에 파일 이름이 포함됩니다.
    @GetMapping(value="{fileName}", produces={ MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public Resource getImage(
        @PathVariable("fileName") String fileName  // URL 경로에서 파일 이름을 추출하여 매개변수로 받습니다.
    ) {
        // FileService의 getImage 메소드를 호출하여 요청된 파일 이름에 해당하는 이미지를 반환합니다.
        Resource resource = fileService.getImage(fileName);
        return resource;  // 이미지 파일을 클라이언트에게 반환합니다.
    }
}
