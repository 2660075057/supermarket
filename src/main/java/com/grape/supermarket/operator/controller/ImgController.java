package com.grape.supermarket.operator.controller;

import com.google.zxing.WriterException;
import com.grape.supermarket.common.util.BarcodeUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by LXP on 2017/12/21.
 */
@Controller
@RequestMapping("/operator/img")
public class ImgController {
    private Logger logger = Logger.getLogger(getClass());

    @RequestMapping("/barcodeImg")
    public void barcodeImg(HttpServletResponse response,String data,Integer width,Integer height,boolean deleteWhite){
        if(data != null && !data.isEmpty()){
            response.setContentType("image/jpeg");
            try(ServletOutputStream outputStream = response.getOutputStream()){
                BufferedImage twoDCode = BarcodeUtil.createTwoCode(data, width != null ? width : 250, height != null ? height : 250,deleteWhite);
                ImageIO.write(twoDCode, "jpeg",outputStream);
                return ;
            } catch (WriterException e) {
                logger.warn("生成二维码识别",e);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        response.setStatus(404);
    }
}
