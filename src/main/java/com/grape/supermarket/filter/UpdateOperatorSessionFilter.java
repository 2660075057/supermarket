package com.grape.supermarket.filter;

import com.grape.supermarket.operator.OperatorSession;
import com.grape.supermarket.operator.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by LXP on 2017/12/1.
 */
@Component("updateOperatorSessionFilter")
public class UpdateOperatorSessionFilter extends OncePerRequestFilter {
    @Autowired
    private OperatorService operatorService;
    private String[] ignorePatten;
    private String[] ignoreUri;

    public UpdateOperatorSessionFilter() {
        ignorePatten = new String[]{"\\S*.js", "\\S*.jpg", "\\S*.css",
                "\\S*.png", "\\S*.gif", "\\S*.html",
                "/operator/assets/\\S*", "/operator/images/\\S*", "/operator/layui/\\S*",
                "/operator/model/\\S*","/operator/page/\\S*"
        };
        ignoreUri = new String[]{"/operator/login.jsp","/operator/user/login","/operator/user/logout"};//配置不需要过滤的url
        Arrays.sort(ignoreUri);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //检查是否需要过滤
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        if (!contextPath.equals("/")) {
            requestURI = requestURI.substring(contextPath.length());
        }
        for (String s : ignorePatten) {
            if (requestURI.matches(s)) {
                filterChain.doFilter(request, response);
                return;
            }
        }
        int i = Arrays.binarySearch(ignoreUri, requestURI);
        if(i>=0){
            filterChain.doFilter(request, response);
            return;
        }


        Object obj = request.getSession().getAttribute(OperatorSession.SESSION_ID);
        if (obj instanceof OperatorSession) {
            OperatorSession operatorSession = ((OperatorSession) obj);
            long updateTime = operatorSession.getUpdateTime();
            if (updateTime + OperatorSession.upTime < System.currentTimeMillis()) {
                if (operatorSession.setUpdateTime(updateTime)) {
                    OperatorService.LoginEnum login = operatorService.login(operatorSession.getOperatorInfo(), request);
                    if(login != OperatorService.LoginEnum.SUCCESS){
                        request.getSession().removeAttribute(OperatorSession.SESSION_ID);
                    }
                }
            }
        }

        filterChain.doFilter(request,response);
    }
}
