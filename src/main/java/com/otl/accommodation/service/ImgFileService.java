package com.otl.accommodation.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class ImgFileService {

    public String uploadImgFile(String uploadPath,String originalFileName, byte[] fileData) throws IOException{
        String uuid = UUID.randomUUID().toString();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = uuid + extension;
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;

        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();

        return savedFileName;
    }

    public void deleteImgFile(String filePath) throws IOException {
        File deleteFile = new File(filePath);

        if (deleteFile.exists()) {
            if (!deleteFile.delete()) {
                throw new IOException("Failed to delete file: " + filePath);
            }
        }
    }
}
