package com._chaumedia.GroupLottery.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;

@Service
public class UploadImgService {
    private final ServletContext servletContext;

    public UploadImgService( ServletContext servletContext) {
        this.servletContext = servletContext;
    }
	
	public String handleSaveUploadImg(MultipartFile file, String targetFolder) {
        if (file.isEmpty()) {
        	return"";
        }
        
        String rootPath = this.servletContext.getRealPath("/resources/admin/assets/img/");
        String finalName = "";
        try {
            byte[] bytes = file.getBytes();

            File dir = new File(rootPath + File.separator + targetFolder);
            if (!dir.exists())
                dir.mkdirs();

            finalName = System.currentTimeMillis() + "-" + file.getOriginalFilename();

            File serverFile = new File(dir.getAbsolutePath() + File.separator + finalName);

            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return finalName;
    }
}
