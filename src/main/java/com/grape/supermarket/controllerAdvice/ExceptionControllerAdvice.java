package com.grape.supermarket.controllerAdvice;

import com.grape.supermarket.common.exception.BeanCopyException;
import com.grape.supermarket.common.exception.FailMessageException;
import com.grape.supermarket.common.exception.UnLoginException;
import com.grape.supermarket.common.exception.WechatFailPageException;
import com.grape.supermarket.entity.ResultBean;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by LXP on 2017/10/14.
 */
@ControllerAdvice
public class ExceptionControllerAdvice {
    private Logger logger = Logger.getLogger(ExceptionControllerAdvice.class);

    @ExceptionHandler(UnLoginException.class)
    @ResponseBody
    public ResultBean unLogin(){
        ResultBean rb = new ResultBean();
        rb.setCode(ResultBean.NOT_LOGIN);
        rb.setMessage("user unLogin");
        return rb;
    }

    @ExceptionHandler(BeanCopyException.class)
    @ResponseBody
    public ResultBean beanCopyExcepton(BeanCopyException ex){
        logger.warn("BeanCopyException",ex);
        ResultBean rb = new ResultBean();
        rb.setCode(ResultBean.FAIL);
        rb.setMessage("FAIL");
        return rb;
    }

    @ExceptionHandler(FailMessageException.class)
    @ResponseBody
    public ResultBean failMessageException(FailMessageException ex){
        if(ex.getLevel() == 0){
            logger.debug("failMessageException",ex);
        }else if(ex.getLevel() == 1){
            logger.info("failMessageException",ex);
        }else if(ex.getLevel() == 2){
            logger.warn("failMessageException",ex);
        }else{
            logger.error("failMessageException",ex);
        }
        ResultBean rb = new ResultBean();
        rb.setCode(ResultBean.FAIL);
        rb.setMessage(ex.getMessage());
        return rb;
    }

    @ExceptionHandler(WechatFailPageException.class)
    public String wechatFailPageException(WechatFailPageException ex){
        if(ex.isNeedLog()){
            logger.warn(ex);
        }
        return "redirect:/wechat/index/error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultBean unKnowExcepton(Exception ex){
        logger.error("unKnowExcepton",ex);
        ResultBean rb = new ResultBean();
        rb.setCode(ResultBean.FAIL);
        rb.setMessage("FAIL");
        return rb;
    }

}
