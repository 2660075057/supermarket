package com.grape.supermarket.operator.controller;

import com.grape.supermarket.common.exception.FailMessageException;
import com.grape.supermarket.entity.ResultBean;
import com.grape.supermarket.entity.UploadFileInfo;
import com.grape.supermarket.operator.service.CommodityService;
import com.grape.supermarket.operator.service.ElecTagService;
import com.grape.supermarket.operator.service.FileService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LXP on 2017/12/2.
 */
@Controller
@RequestMapping("/operator/file")
public class FileController {
    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private ElecTagService elecTagService;

    @Autowired
    private FileService fileService;
    
    @Autowired
    private CommodityService commodityService;

    @RequestMapping("/img")
    @ResponseBody
    public ResultBean<List<String>> uploadImg(@RequestParam("file") MultipartFile[] file, HttpServletRequest request,String imgType) {
        if(imgType == null || imgType.isEmpty()){
            throw new FailMessageException("imgType is null");
        }
        for (MultipartFile multipartFile : file) {
            String originalFilename = multipartFile.getOriginalFilename();
            int i = originalFilename.lastIndexOf('.');
            if (i == -1 || i == originalFilename.length() - 1) {
                throw new FailMessageException(originalFilename + " not support");
            }
        }

        //write file
        List<String> path = new ArrayList<>();
        for (MultipartFile multipartFile : file) {
            String image = fileService.saveFile(multipartFile, imgType, request.getSession().getServletContext());
            if(image == null){
                throw new FailMessageException("save file fail");
            }
            path.add(image);
        }
        ResultBean<List<String>> rb = new ResultBean<>();
        rb.setData(path);
        return rb;
    }
    
    @RequestMapping("/upShopData")
    @ResponseBody
    public ResultBean<String> uploadTagData(MultipartFile file, HttpServletRequest request){
        String originalFilename = file.getOriginalFilename();
        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
        request.getSession().setAttribute("fileType",fileType);
        ResultBean<String> rb = new ResultBean<>();
        if(originalFilename.endsWith(".xls")
                || originalFilename.endsWith(".xlsx")){
            UploadFileInfo fileInfo = new UploadFileInfo();
            fileInfo.setFile(file);
           if(originalFilename.endsWith(".xls")){
                fileInfo.setFileType(UploadFileInfo.FILE_TYPE.XLS);
            }else if(originalFilename.endsWith(".xlsx")){
                fileInfo.setFileType(UploadFileInfo.FILE_TYPE.XLSX);
            }
            request.getSession().setAttribute("importCommodity",fileInfo);
            rb.setData("importCommodity");
        }else{
            rb.setCode(ResultBean.FAIL);
            rb.setMessage("不合法的文件类型");
        }
        return rb;
    }
    
}
