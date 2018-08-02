package com.grape.supermarket.filter;

import com.grape.supermarket.common.OperatorSessionHelper;
import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by LXP on 2017/10/14.
 */

public class OperatorFilter extends OncePerRequestFilter {
    private Logger logger = Logger.getLogger(OncePerRequestFilter.class);

    private String[] ignorePatten;
    private String[] ignoreUri;

    public OperatorFilter() {
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
        if (i < 0) {
            try {
                boolean b = OperatorSessionHelper.saveSession(request);
                if (!b) {//不存在用户session
                    logger.info("用户session不存在");
                    String url = contextPath.equals("/") ? "/operator/user/login" : contextPath + "/operator/user/login";
                    response.sendRedirect(url);
                    return;
                }
                filterChain.doFilter(request, response);
            } finally {
                OperatorSessionHelper.clearSessionCache();
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

}
