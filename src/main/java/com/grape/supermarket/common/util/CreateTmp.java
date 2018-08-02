package com.grape.supermarket.common.util;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

public class CreateTmp {
	/**
     * 创建临时文件  createDir
     * @param prefix    临时文件名的前缀
     * @param suffix    临时文件名的后缀
     * @param dirName   临时文件所在的目录，如果输入null，则在用户的文档目录下创建临时文件
     * @return 临时文件创建成功返回true，否则返回false
     */
	private static Logger logger = Logger.getLogger(CreateTmp.class);
	
    public static String createTempFile(String prefix, String suffix,
            String dirName) {
        File tempFile = null;
        if (dirName == null) {
            try {
                // 在默认文件夹下创建临时文件
                tempFile = File.createTempFile(prefix, suffix);
                // 返回临时文件的路径
                return tempFile.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
                logger.warn("创建临时文件失败!" + e.getMessage());
                return null;
            }
        } else {
            File dir = new File(dirName);
            // 如果临时文件所在目录不存在，首先创建
            if (!dir.exists()) {
                if (createDir(dirName)) {
                    logger.warn("创建临时文件失败，不能创建临时文件所在的目录！");
                    return null;
                }
            }
            try {
                // 在指定目录下创建临时文件
                tempFile = File.createTempFile(prefix, suffix, dir);
                return tempFile.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
                logger.warn("创建临时文件失败!" + e.getMessage());
                return null;
            }
        }
    }
    
    /** 
     * 创建目录   dir.mkdirs(
     * @param destDirName   目标目录名
     * @return 目录创建成功放回true，否则返回false
     */
    private static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            logger.warn("创建目录" + destDirName + "失败，目标目录已存在！");
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        // 创建目标目录
        if (dir.mkdirs()) {
            logger.warn("创建目录" + destDirName + "成功！");
            return true;
        } else {
            logger.warn("创建目录" + destDirName + "失败！");
            return false;
        }
    }
	
}
