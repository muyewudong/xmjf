package com.shsxt.xm.web.controller;


import com.shsxt.xm.api.constant.P2pConstant;
import com.shsxt.xm.api.dto.AccountDto;
import com.shsxt.xm.api.dto.PayDto;
import com.shsxt.xm.api.model.ResultInfo;
import com.shsxt.xm.api.po.BusAccount;
import com.shsxt.xm.api.po.BusAccountRecharge;
import com.shsxt.xm.api.po.User;
import com.shsxt.xm.api.query.AccountRechargeQuery;
import com.shsxt.xm.api.service.IBusAccountService;
import com.shsxt.xm.api.utils.PageList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("account")
public class BasAccountController {

    @Resource
    private IBusAccountService busAccountService;

    @RequestMapping("rechargePage")
    public String index(HttpServletRequest request){
        request.getSession().setAttribute("ctx",request.getContextPath());
        return "user/recharge";
    }


    /**
     * 发起支付请求转发页面
     * @return
     */
    @RequestMapping("doAccountRechargeToRechargePage")
    public String doAccountRechargeToRechargePage(BigDecimal amount, String picCode, String bussinessPassword, HttpServletRequest request,Model model){
        request.getSession().setAttribute("ctx",request.getContextPath());
        String sessionPicCode = (String) request.getSession().getAttribute(P2pConstant.PICTURE_VERIFY_KEY);
        if(StringUtils.isBlank(picCode)){
            System.out.println("验证码失效");
            return "user/pay";
        }
        if(!picCode.equals(sessionPicCode)){
            System.out.println("验证码失效");
            return "user/pay";
        }
        User user = (User) request.getSession().getAttribute("userInfo");
        PayDto payDto = busAccountService.buildRechargeRequestInfo(amount, bussinessPassword, user.getId());
        model.addAttribute("pay",payDto);
        return "user/pay";
    }


    @RequestMapping("queryRechargeRecodesByUserId")
    @ResponseBody
    public PageList queryRechargeRecodesByUserId(AccountRechargeQuery accountRechargeQuery,HttpServletRequest request){
        request.getSession().setAttribute("ctx",request.getContextPath());
        User user = (User) request.getSession().getAttribute("userInfo");
        //AccountRechargeQuery accountRechargeQuery = new AccountRechargeQuery();
        accountRechargeQuery.setUserId(user.getId());
        return busAccountService.queryRechargeRecodesByParams(accountRechargeQuery);
    }


    @RequestMapping("rechargeRecord")
    public String rechargeRecord(HttpServletRequest request){
        request.getSession().setAttribute("ctx",request.getContextPath());
        return "user/recharge_record";
    }




    @RequestMapping("callback")
    public String callback(){

        return "";
    }

    @RequestMapping("accountInfoPage")
    public String accountInfo(HttpServletRequest request){
        request.getSession().setAttribute("ctx",request.getContextPath());
        return "user/account_info";
    }

    @RequestMapping("accountInfo")
    @ResponseBody
    public List<AccountDto> accountInfo(HttpSession session){
        User user = (User) session.getAttribute("userInfo");
        Integer userId = user.getId();
        return busAccountService.accountInfo(userId);
    }



}
