package com.example.back.cloud.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileCompressionUtil {

    // Method to compress a byte array
    public static byte[] compress(byte[] data, String filename) throws IOException {
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

    // Method to decompress a byte array
    public static byte[] decompress(byte[] compressedData) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(new java.io.ByteArrayInputStream(compressedData));
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ZipEntry entry = zipInputStream.getNextEntry();
            if (entry != null) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = zipInputStream.read(buffer)) > 0) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }
            }
            return byteArrayOutputStream.toByteArray();
        }
    }
}
