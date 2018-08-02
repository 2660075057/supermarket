package com.grape.supermarket.operator.service;
 
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.db.OrderEntity;
import com.grape.supermarket.entity.page.FinanceDetailPageEntity;
import com.grape.supermarket.entity.page.FinancePageEntity;
import com.grape.supermarket.entity.page.OrderPageEntity;
import com.grape.supermarket.entity.param.FinanceDetailParam;
import com.grape.supermarket.entity.param.OrderParam;
import com.grape.supermarket.event.PaySuccessEvent;
 
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
 
/**
 * Created by LQW on 2017/10/18.
 */
 
public interface OrderService {
    /**
     * 根据顾客ID分页查询订单记录
     * @return
     */
    List<OrderPageEntity> wechatSelectOrderByCoustId(PageParam pageParam);
    /**
     * 根据顾客ID统计订单总记录数
     * @return
     */
    int wechatcountOrderByCoustId();
 
    /**
     * 顾客删除订单
     * @return
     */
    boolean wechatDeleteById(Integer orderId);
 
    void paySuccessEvent(PaySuccessEvent event);
    /**
     * 财经统计
     * @param param
     * @return
     */
    List<FinancePageEntity> financeStatistics(FinancePageEntity param);
 
    /**
     * 财经统计详情
     * @param param
     * @return
     */
    List<FinanceDetailPageEntity> financeStatisticsDetail(FinanceDetailParam param,PageParam page);
    int countStatisticsDetail(FinanceDetailParam param);
 
    /**
     * 导出财经详情
     * @param shopId 商店id
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param response response
     */
    void exportFinanceStatisticsDetail(Integer shopId, Date startDate, Date endDate, HttpServletResponse response);
 
    /**
     * 查询订单
     * author huanghuang
     * 2017年11月17日 上午10:37:51
     * @param orderParam
     * @return
     */
    List<OrderPageEntity> selectOrderByParam(OrderParam orderParam, PageParam page);
    
    /**
     *  查询订单数量
     * author huanghuang
     * 2017年11月17日 上午11:41:31
     * @param orderParam
     * @return
     */
    int countByParam(OrderParam param);
    
    /**
     * 通过订单ID查询一条订单
     * author huanghuang
     * 2017年11月17日 下午3:27:06
     * @param orderId
     * @return
     */
    OrderPageEntity selectByPrimaryKey(Integer orderId);
 
    OrderEntity selectByPaymentId(String paymentId);
 
    boolean cancelOrder(String paymentId);
    
    /**
     * 导出销售详情
     * @param param 
     * @param response responce
     */
    void exportSaleStatisticsDetail(OrderParam param, HttpServletResponse response);
}
