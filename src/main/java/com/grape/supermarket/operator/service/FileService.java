package com.grape.supermarket.operator.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;

/**
 * Created by LXP on 2018/3/3.
 */

public interface FileService {
    /**
     * 保存一个文件到服务器
     * @param file 文件
     * @param classno 文件分类标识
     * @param context 服务器上下文
     * @return 文件保存路径
     */
    String saveFile(MultipartFile file, String classno, ServletContext context);
}
