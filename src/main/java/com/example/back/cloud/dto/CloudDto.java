package com.example.back.cloud.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CloudDto {
    // Add fields relevant for cloud file handling, e.g., filename, file size, status
    private String filename;
    private long fileSize;
    private String status;

    // Constructors
    public CloudDto() {
    }

    public CloudDto(String filename, long fileSize, String status) {
        this.filename = filename;
        this.fileSize = fileSize;
        this.status = status;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "CloudDto{" +
                "filename='" + filename + '\'' +
                ", fileSize=" + fileSize +
                ", status='" + status + '\'' +
                '}';
    }
}
