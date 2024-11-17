package com.example.back.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
public class CloudEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private long fileSize;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String folderName; // 폴더 이름 필드 추가

    // 기본 생성자
    public CloudEntity() {
    }

    // 모든 필드를 포함한 생성자
    public CloudEntity(String filename, long fileSize, String filePath, String folderName) {
        this.filename = filename;
        this.fileSize = fileSize;
        this.filePath = filePath;
        this.folderName = folderName;
    }

    // Getter 및 Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    // 디버깅을 위한 toString 메서드
    @Override
    public String toString() {
        return "CloudEntity{" +
                "id=" + id +
                ", filename='" + filename + '\'' +
                ", fileSize=" + fileSize +
                ", filePath='" + filePath + '\'' +
                ", folderName='" + folderName + '\'' +
                '}';
    }
}
