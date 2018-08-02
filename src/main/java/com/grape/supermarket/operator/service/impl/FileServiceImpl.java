package com.grape.supermarket.operator.service.impl;

import com.grape.supermarket.operator.service.FileService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by LXP on 2018/3/3.
 */
@Service
public class FileServiceImpl implements FileService {
    private Logger logger = Logger.getLogger(getClass());
    private final String UPLOAD_DIR = "upload";//保存上传文件根目录

    @Override
    public String saveFile(MultipartFile file, String classno, ServletContext context) {
        String servletPath = context.getRealPath("/");
        File parentFile = new File(servletPath + UPLOAD_DIR + "/" + classno + "/");
        if(!parentFile.exists()){
            synchronized (this){
                if(!parentFile.exists()){
                    if(!parentFile.mkdirs()){
                        logger.error("创建文件目录失败["+parentFile+"]");
                        return null;
                    }
                }
            }
        }
        String originalFilename = file.getOriginalFilename();
        int suffixIndex = originalFilename.indexOf('.');
        String newFilename = UUID.randomUUID().toString().replace("-", "") + originalFilename.substring(suffixIndex);
        File newFile = new File(parentFile, newFilename);
        try {
            newFile.createNewFile();
            file.transferTo(newFile);
            return "/" + UPLOAD_DIR + "/" + classno + "/" + newFilename;
        } catch (IOException e) {
            logger.error("保存文件失败", e);
        }

        return null;
    }
}
