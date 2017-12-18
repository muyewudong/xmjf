package com.shsxt.xm.web.controller;


import com.shsxt.xm.api.constant.P2pConstant;
import com.shsxt.xm.api.dto.InvestDto;
import com.shsxt.xm.api.exceptions.ParamsExcetion;
import com.shsxt.xm.api.model.ResultInfo;
import com.shsxt.xm.api.po.User;
import com.shsxt.xm.api.service.IBusItemInvestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("busItemInvest")
public class BusItemInvestController {

    @Resource
    private IBusItemInvestService busItemInvestService;

    @RequestMapping("userInvest")
    @ResponseBody
    public ResultInfo userInvest(HttpSession session, Integer itemId, BigDecimal amount, String businessPassword){
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(P2pConstant.SUCCESS_CODE);
        resultInfo.setMsg(P2pConstant.SUCCESS_MSG);
        try {
            User user = (User) session.getAttribute("userInfo");
            Integer userId = user.getId();
            busItemInvestService.addBusItemInvest(userId,itemId,amount,businessPassword);
        } catch (ParamsExcetion e) {
            e.printStackTrace();
            resultInfo.setMsg(e.getErrorMsg());
            resultInfo.setCode(e.getErrorCode());
        }catch (Exception e) {
            e.printStackTrace();
            resultInfo.setCode(P2pConstant.FAILED_CODE);
            resultInfo.setMsg(P2pConstant.FAILED_MSG);
        }

        return resultInfo;
    }


    @RequestMapping("investInfo")
    @ResponseBody
    public Map<String,Object[]> investInfo(HttpSession session){
        User user = (User) session.getAttribute("userInfo");
        return busItemInvestService.investInfo(user.getId());

    }




}
