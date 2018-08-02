package com.grape.supermarket.common;


import com.grape.supermarket.common.exception.UnLoginException;
import com.grape.supermarket.entity.db.CustomerEntity;
import com.grape.supermarket.operator.service.CustomerService;
import com.grape.supermarket.wechat.WechatSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by LXP on 2017/9/28.
 */

public final class WechatSessionHelper {
    private static ThreadLocal<WechatSession> threadLocal = new ThreadLocal<>();

    /**
     * 保存session，<font color="red">仅能在request调用链中使用</font>
     * @param request request
     * @return 若成功返回true
     */
    public static boolean saveSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        WechatSession customerSession = (WechatSession) session.getAttribute(WechatSession.SESSION_ID);
        CustomerService cs = ApplicationContextHelper.getApplicationContext().getBean(CustomerService.class);
        if(cs != null && customerSession == null){
            String openId = request.getParameter("openId");
            if(openId != null){
                CustomerEntity customer = cs.getCustomerByOpenId(openId);
                if(customer != null){
                    customerSession = new WechatSession(customer);
                    session.setAttribute(WechatSession.SESSION_ID,customerSession);
                }
            }else{
                return false;
            }
        }
        return saveSession(customerSession);
    }

    /**
     * 保存session
     * @param operatorSession operatorSession
     * @return 若成功返回true
     */
    public static boolean saveSession(WechatSession operatorSession) {
        if(operatorSession == null){
            return false;
        }
        threadLocal.set(operatorSession);
        return true;
    }

    /**
     * 获取wechatSession，<font color="red">仅能在request调用链中使用</font>
     * @return 若存在返回，否则返回null
     */
    public static WechatSession getSession(){
        return threadLocal.get();
    }

    /**
     * 获取wechatSession，<font color="red">仅能在request调用链中使用</font>
     * @throws UnLoginException 若不存在wechatSession抛出异常
     * @return 若存在返回，否则返回抛出异常
     */
    public static WechatSession getSessionOrThrow(){
        WechatSession session = getSession();
        if(session == null){
            throw new UnLoginException();
        }
        return session;
    }

    /**
     * 清除session缓存。请注意，无法使用此方法清除request中的session值
     */
    public static void clearSessionCache(){
        threadLocal.remove();
    }
}
