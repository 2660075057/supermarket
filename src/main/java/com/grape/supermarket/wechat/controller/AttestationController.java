package com.grape.supermarket.wechat.controller;

import com.grape.supermarket.common.WechatSessionHelper;
import com.grape.supermarket.common.util.StringUtils;
import com.grape.supermarket.entity.ResultBean;
import com.grape.supermarket.entity.db.CustomerEntity;
import com.grape.supermarket.operator.service.CustomerService;
import com.grape.supermarket.operator.service.MobileMessageService;
import com.grape.supermarket.wechat.WechatSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by LXP on 2018/1/12.
 */
@Controller
@RequestMapping("/wechat/attestation")
public class AttestationController {
    @Autowired
    private MobileMessageService mobileMessageService;

    @Autowired
    private CustomerService customerService;

    @RequestMapping()
    public ModelAndView attestation() {
        WechatSession session = WechatSessionHelper.getSession();
        if (session == null) {
            return new ModelAndView("redirect:/wechat/index/error");
        }
        ModelAndView mav = new ModelAndView("wechatPage/attestation");
        mav.addObject("hasAuth",session.getPhone() != null);
        return mav;
    }

    @RequestMapping("/verify")
    public ModelAndView verify(String phone) {
        WechatSession session = WechatSessionHelper.getSession();
        if (session == null || session.getPhone() != null || phone == null) {
            return new ModelAndView("redirect:/wechat/index/error");
        }
        ModelAndView mav = new ModelAndView("wechatPage/verify");
        mav.addObject("phone", phone);
        return mav;
    }

    @RequestMapping("/sendRegisterMessage")
    @ResponseBody
    public ResultBean<Integer> sendRegisterMessage(String phone, HttpSession session) {
        ResultBean<Integer> rb = new ResultBean<>();
        if (phone == null || phone.isEmpty()) {
            rb.setData(-1);
            rb.setMessage("phone error");
            return rb;
        }
        WechatSession wechatSession = WechatSessionHelper.getSession();
        if (wechatSession == null) {
            rb.setData(-2);
            rb.setMessage("unLogin");
            return rb;
        }
        Long lastSend = (Long) session.getAttribute("lastSend");
        if (lastSend != null && lastSend + 60_000 > System.currentTimeMillis()) {
            rb.setData(1);
            rb.setMessage("Interval Too Brief");
            return rb;
        }
        String numVcode = StringUtils.createNumVcode(4);
        int i = mobileMessageService.sendRegisterMessage(phone, numVcode);
        if (i == 0) {
            session.setAttribute("lastSend", System.currentTimeMillis());
            session.setAttribute("wechat_vcode", numVcode);
            session.setAttribute("wechat_phone", phone);
        } else if (i == 1) {
            rb.setData(1);
            rb.setMessage("Interval Too Brief");
            return rb;
        } else if (i == -1) {
            rb.setCode(ResultBean.FAIL);
            rb.setMessage("error");
            return rb;
        }

        rb.setData(0);
        return rb;
    }

    @RequestMapping("/verifyCode")
    @ResponseBody
    public ResultBean<Integer> verifyCode(String code,HttpSession session){
        ResultBean<Integer> rb = new ResultBean<>();

        WechatSession wechatSession = WechatSessionHelper.getSession();
        Long lastSend = (Long) session.getAttribute("lastSend");
        String vcode = (String) session.getAttribute("wechat_vcode");
        String phone = (String) session.getAttribute("wechat_phone");

        if (wechatSession == null) {
            rb.setData(-1);
        } else if (lastSend == null || lastSend + 300_000 < System.currentTimeMillis()) {
            rb.setData(1);//验证码过期
        } else if (phone == null || vcode == null || !vcode.equals(code)) {
            rb.setData(2);//验证码错误
        } else {
            CustomerEntity param = new CustomerEntity();
            param.setCoustId(wechatSession.getCoustId());
            param.setPhone(phone);
            customerService.updateCustomer(param);
            rb.setData(0);
        }
        return rb;
    }
}
