package com.grape.supermarket.common;


import com.grape.supermarket.common.util.PropertiesLoader;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.SelectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 基础服务
 * @author xunpengliu
 * @version 创建时间：2017年5月23日 上午9:45:58
 */
public abstract class BasicService extends AbstractLogger {
	
	@Autowired
    @Qualifier("properties")
	private PropertiesLoader properties;

	protected PropertiesLoader getProperties() {
		return properties;
	}

	protected SelectEntity initSelectEntity(PageParam pageParam){
        SelectEntity select = new SelectEntity();
        if (pageParam != null) {
            int[] limit = pageParam.getLimit();
            if (limit != null) {
                select.setLimitS(limit[0]);
                select.setLimitE(limit[1]);
            }
        }
        return select;
    }
}
