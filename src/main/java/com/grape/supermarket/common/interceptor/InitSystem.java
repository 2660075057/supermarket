package com.grape.supermarket.common.interceptor;

import com.grape.supermarket.common.util.PropertiesLoader;
import com.grape.supermarket.wechat.service.WechatService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * 启动服务的时候调用的方法
 * 
 * <p>
 * 2016年3月22日 下午5:32:10
 * 
 * @author hjc
 * 
 */
public class InitSystem implements InitializingBean, ServletContextAware {

    @Autowired
    private WechatService wechatService;

    @Autowired
    @Qualifier("wechatProperties")
    private PropertiesLoader pl;

	@Override
	public void afterPropertiesSet() throws Exception {
        boolean pushMenu = pl.getBoolean("pushMenu",false);
        if(pushMenu) {
            wechatService.setWechatMenu();
        }
	}

	@Override
	public void setServletContext(ServletContext servletContext) {

	}

}
