package com.grape.supermarket.filter;

import com.grape.supermarket.common.WechatSessionHelper;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by LXP on 2017/10/24.
 */

public class WechatFilter extends OncePerRequestFilter {

    private final String[] ignorePatten;
    private final String[] ignoreUri;

    public WechatFilter() {
        ignorePatten = new String[]{"\\S*.js", "\\S*.jpg", "\\S*.css",
                "\\S*.png", "\\S*.gif", "\\S*.html","/wechat/index/\\S*"
        };
        ignoreUri = new String[]{};//配置不需要过滤的url
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
                WechatSessionHelper.saveSession(request);
                filterChain.doFilter(request,response);
            } finally {
                WechatSessionHelper.clearSessionCache();
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
