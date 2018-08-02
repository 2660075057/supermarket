package com.grape.supermarket.filter;

import com.grape.supermarket.common.OperatorSessionHelper;
import com.grape.supermarket.dao.PermissionDao;
import com.grape.supermarket.entity.db.PermissionEntity;
import com.grape.supermarket.operator.OperatorSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by LXP on 2018/4/11.
 * 操作员权限过滤器
 */
@Component("operatorPermissionFilter")
public class OperatorPermissionFilter extends OncePerRequestFilter {
    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private PermissionDao permissionDao;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        if (!contextPath.equals("/")) {
            requestURI = requestURI.substring(contextPath.length());
        }
        //去除多反斜杠
        requestURI =  requestURI.replaceAll("/{2,}","/");
        OperatorSession session = OperatorSessionHelper.getSession();
        if (session != null && session.getOperatorInfo().getOperId() != 0) {
            List<PermissionEntity> permissionList = permissionDao.selectByParam(null);
            //检查请求连接是否为受保护资源
            PermissionEntity securityPermission = null;//受保护资源权限信息
            for (PermissionEntity permissionEntity : permissionList) {
                if (hasUrl(permissionEntity, requestURI)) {
                    securityPermission = permissionEntity;
                    break;
                }
            }

            if (securityPermission != null) {
                List<PermissionEntity> permissions = session.getPermissions();
                if (permissions == null) {
                    logger.warn("操作员未含有任何权限");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return;
                } else {
                    boolean hasPermission = false;
                    for (PermissionEntity permission : permissions) {//检查是否在用户权限范围内
                        if (permission.getId().equals(securityPermission.getId())) {
                            hasPermission = true;
                            break;
                        }
                    }
                    if (!hasPermission) {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        return;
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean hasUrl(PermissionEntity permissionEntity, String url) {
        String[] permissionUrl = permissionEntity.getUrl().split(",");

        for (String s : permissionUrl) {
            if (s.equalsIgnoreCase(url)) {
                return true;
            }
        }
        return false;
    }
}
