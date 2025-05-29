package com.spring.service.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation; // 파일이 저장될 최종 디렉토리 경로 객체

    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);
    
    /**
     * 생성자: application.properties(또는 keys.properties)에서 파일 저장 경로를 주입받아
     * Path 객체로 변환하고 저장합니다.
     * ★★★ 중요: 디렉토리 생성 로직은 여기서 제거합니다! ★★★
     * @param uploadDir 설정 파일에서 주입받은 업로드 디렉토리 경로 문자열
     */
    public FileStorageService(@Value("${file.uploadDir}") String uploadDir) {
        // 1. 주입받은 경로 문자열을 Path 객체로 변환하고 정규화/절대경로화
        this.fileStorageLocation = Paths.get(uploadDir.trim()).toAbsolutePath().normalize();
        logger.info("FileStorageService 초기화 완료 - 설정된 업로드 경로: {}", this.fileStorageLocation);
        // 2. 여기서 디렉토리 생성 시도(Files.createDirectories)를 제거하여 앱 시작 시 권한 오류 방지
        //    디렉토리 생성은 실제 파일 저장 시점에 수행합니다.
    }

    /**
     * 전달받은 MultipartFile을 지정된 외부 저장소에 저장하고,
     * 서버에 저장된 고유 파일명을 반환합니다.
     * @param file 저장할 MultipartFile 객체
     * @return 서버에 저장된 고유 파일명 (예: "uuid.jpg")
     * @throws IOException 파일 저장 또는 디렉토리 생성 중 오류 발생 시
     */
    public String storeFile(MultipartFile file) throws IOException {
        // 1. 원본 파일명 가져오기 및 정리
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        logger.info("storeFile 호출됨 - 원본 파일명: {}", originalFilename);

        // 2. 파일명 유효성 검사 (선택 사항)
        if (originalFilename.contains("..")) {
            throw new IOException("파일명에 잘못된 경로 문자가 포함되어 있습니다: " + originalFilename);
        }

        // 3. 확장자 추출
        String fileExtension = "";
        int dotIndex = originalFilename.lastIndexOf('.');
        if (dotIndex >= 0 && dotIndex < originalFilename.length() - 1) { // '.'이 맨 앞이나 맨 뒤는 아닌지 확인
            fileExtension = originalFilename.substring(dotIndex); // 예: ".jpg"
        } else {
        	logger.error("파일 확장자를 찾을 수 없거나 유효하지 않습니다: {}", originalFilename);
            // 확장자가 없는 경우 어떻게 처리할지 결정 (예: 기본 확장자 부여, 오류 발생 등)
            // fileExtension = ".unknown"; // 예시
        }

        // 4. UUID를 이용한 고유 파일명 생성
        String storedFilename = UUID.randomUUID().toString() + fileExtension;
        logger.info("서버 저장 파일명 생성: {}", storedFilename);
        // 6. 최종 저장 파일 경로 계산
        Path targetLocation = this.fileStorageLocation.resolve(storedFilename);
        logger.info("최종 저장 파일 경로: {}", targetLocation.toString());

        // 7. 파일 저장 (Files.copy 사용 권장)
        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            logger.info("파일 저장 성공: " + targetLocation);
        } catch (IOException ex) {
        	logger.error("파일 저장 실패! 경로: {}, 파일명: {}, 오류: {}", targetLocation, storedFilename, ex.getMessage(), ex);
            ex.printStackTrace();
            // 사용자에게 보여줄 더 친절한 메시지와 함께 예외 다시 던지기
            throw new IOException("파일 [" + originalFilename + "] 을 저장할 수 없습니다.", ex);
        } catch (Exception ex) { // 기타 예외 (예: IllegalStateException)
        	logger.error("파일 저장 중 예상치 못한 오류 발생! 파일명: {}, 오류: {}", storedFilename, ex.getMessage(), ex);
            throw new IOException("파일 [" + originalFilename + "] 저장 중 오류 발생.", ex);
        }

        // 8. 저장된 파일명 반환
        return storedFilename;
    }

    /**
     * 파일명으로 실제 파일 경로(Path)를 반환하는 메소드 (파일 다운로드 등에 사용 가능)
     * @param filename 찾을 파일명 (예: "uuid.jpg")
     * @return 해당 파일의 Path 객체
     */
    public Path loadFileAsPath(String filename) {
        return this.fileStorageLocation.resolve(filename).normalize();
    }
}