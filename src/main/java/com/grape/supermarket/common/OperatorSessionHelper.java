package com.grape.supermarket.common;

import com.grape.supermarket.common.exception.UnLoginException;
import com.grape.supermarket.common.util.StringUtils;
import com.grape.supermarket.entity.db.PermissionEntity;
import com.grape.supermarket.operator.OperatorSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by LXP on 2017/9/25.
 * 此工具仅能在request调用链中使用
 */

public final class OperatorSessionHelper {
    private static ThreadLocal<OperatorSession> threadLocal = new ThreadLocal<>();
    private static ThreadLocal<String> clientIp = new ThreadLocal<>();
    private OperatorSessionHelper(){ }

    /**
     * 保存session，<font color="red">仅能在request调用链中使用</font>
     * @param request request
     * @return 若成功返回true
     */
    public static boolean saveSession(HttpServletRequest request){
        clientIp.set(StringUtils.getIpAddr(request));
        HttpSession session = request.getSession();
        Object obj = session.getAttribute(OperatorSession.SESSION_ID);
        if(!(obj instanceof OperatorSession)){
            return false;
        }
        return saveSession(((OperatorSession) obj));
    }

    /**
     * 保存session
     * @param operatorSession operatorSession
     * @return 若成功返回true
     */
    private static boolean saveSession(OperatorSession operatorSession) {
        threadLocal.set(operatorSession);
        return true;
    }

    /**
     * 获取operatorSession，<font color="red">仅能在request调用链中使用</font>
     * @return 若存在返回，否则返回null
     */
    public static OperatorSession getSession(){
        return threadLocal.get();
    }

    /**
     * 获取operatorSession，<font color="red">仅能在request调用链中使用</font>
     * @throws UnLoginException 若不存在operatorSession抛出异常
     * @return 若存在返回，否则返回抛出异常
     */
    public static OperatorSession getSessionOrThrow(){
        OperatorSession session = getSession();
        if(session == null){
            throw new UnLoginException();
        }
        return session;
    }

    public static String getClientIp(){
        return clientIp.get();
    }

    /**
     * 清除session缓存。请注意，无法使用此方法清除request中的session值
     */
    public static void clearSessionCache(){
        threadLocal.remove();
        clientIp.remove();
    }

    public static boolean hasPermission(Integer id){
        OperatorSession session = getSession();
        if(session == null){
            return false;
        }
        for (PermissionEntity permissionEntity : session.getPermissions()) {
            if(permissionEntity.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    public static boolean hasPermission(String code){
        OperatorSession session = getSession();
        if(session == null){
            return false;
        }
        for (PermissionEntity permissionEntity : session.getPermissions()) {
            if(permissionEntity.getCode().equals(code)){
                return true;
            }
        }
        return false;
    }
}
