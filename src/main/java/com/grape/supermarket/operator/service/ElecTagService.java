package com.grape.supermarket.operator.service;

import com.grape.supermarket.entity.db.ElecTagEntity;
import com.grape.supermarket.entity.page.ElecTagPageEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by LQW on 2017/12/2.
 */

public interface ElecTagService {
    /**
     * 创建电子标签列表，保证尽最大努力生成电子标签
     * @param commId 商品id
     * @param userData 用户自定义数据
     * @param num 电子标签数据
     * @return 创建成功的电子标签列表
     */
    List<ElecTagEntity> createElecTag(Integer commId, Map<String,String> userData, int num);

    /**
     * 导入EPC
     * @param elecTag 电子标签数据
     * @return excel下载句柄，返回null说明未导入任何条码
     */
    Integer importElecTag(List<ElecTagPageEntity> elecTag);

    /**
     * 下载excel
     * @param downloadHandle 下载句柄
     * @param response responce
     */
    void downLoad(Integer downloadHandle,HttpServletResponse response);
}
