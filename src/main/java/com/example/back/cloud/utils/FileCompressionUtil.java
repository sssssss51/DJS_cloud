package com.example.back.cloud.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileCompressionUtil {

    /**
     * 압축 메서드: 주어진 데이터를 압축하고 파일 이름을 설정합니다.
     *
     * @param data     압축할 데이터
     * @param filename 압축 파일 이름
     * @return 압축된 데이터
     * @throws IOException 압축 중 오류 발생 시
     */
    public static byte[] compress(byte[] data, String filename) throws IOException {
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("압축할 데이터가 비어 있습니다.");
        }
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("파일 이름이 유효하지 않습니다.");
        }

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
            ZipEntry entry = new ZipEntry(filename);
            zipOutputStream.putNextEntry(entry);
            zipOutputStream.write(data);
            zipOutputStream.closeEntry();
            zipOutputStream.finish();
            return byteArrayOutputStream.toByteArray();
        }
    }

    /**
     * 압축 해제 메서드: 주어진 압축 데이터를 해제합니다.
     *
     * @param compressedData 압축된 데이터
     * @return 압축 해제된 데이터
     * @throws IOException 압축 해제 중 오류 발생 시
     */
    public static byte[] decompress(byte[] compressedData) throws IOException {
        if (compressedData == null || compressedData.length == 0) {
            throw new IllegalArgumentException("압축 해제할 데이터가 비어 있습니다.");
        }

        try (ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(compressedData));
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ZipEntry entry = zipInputStream.getNextEntry();
            if (entry == null) {
                throw new IOException("압축 해제에 실패했습니다. 유효한 압축 데이터가 아닙니다.");
            }
            byte[] buffer = new byte[1024];
            int len;
            while ((len = zipInputStream.read(buffer)) > 0) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            zipInputStream.closeEntry();
            return byteArrayOutputStream.toByteArray();
        }
    }
}
