package com.example.foodai.util;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public class ImageUtil {

    public static byte[] convertMultipartFileToBytes(MultipartFile file) throws IOException {
        return file.getBytes();
    }
}
