package com.grape.supermarket.entity;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by LXP on 2017/12/2.
 */

public class UploadFileInfo {
    public enum FILE_TYPE{TXT,XLS,XLSX}
    private MultipartFile file;
    private FILE_TYPE fileType;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public FILE_TYPE getFileType() {
        return fileType;
    }

    public void setFileType(FILE_TYPE fileType) {
        this.fileType = fileType;
    }
}
