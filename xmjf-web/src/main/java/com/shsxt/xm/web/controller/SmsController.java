package com.shsxt.xm.web.controller;

import com.shsxt.xm.api.exceptions.ParamsExcetion;
import com.shsxt.xm.api.model.ResultInfo;
import com.shsxt.xm.api.service.ISmsService;
import com.shsxt.xm.api.service.IUserService;
import com.shsxt.xm.api.utils.AssertUtil;
import com.shsxt.xm.api.constant.P2pConstant;
import com.shsxt.xm.api.utils.RandomCodesUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("sms")
public class SmsController {

    @Resource
    private ISmsService smsService;

    @RequestMapping("sendPhoneSms")
    @ResponseBody
    public ResultInfo sendPhoneSms(String phone, String picCode, Integer type, HttpSession session){
        ResultInfo resultInfo = new ResultInfo();
        String sessionCode = (String) session.getAttribute(P2pConstant.PICTURE_VERIFY_KEY);
        if(StringUtils.isBlank(picCode)){
            resultInfo.setCode(P2pConstant.FAILED_CODE);
            resultInfo.setMsg("验证码已失效");
            return resultInfo;
        }

        if(!picCode.equals(sessionCode)){
            resultInfo.setCode(P2pConstant.FAILED_CODE);
            resultInfo.setMsg("验证码不匹配");
            return resultInfo;
        }

        try {
            String code = RandomCodesUtils.createRandom(true, 4);
            System.out.println("code:"+code);
            smsService.sendPhoneSms(phone,code,type);
            //手机验证码存入session
            session.setAttribute(P2pConstant.PHONE_VERIFY_CODE+phone,code);
            //发送手机验证码当前时间存入session
            session.setAttribute(P2pConstant.PHONE_VERIFY_CODE_EXPIRE_TIME+phone,new Date());
            resultInfo.setCode(P2pConstant.SUCCESS_CODE);
            resultInfo.setMsg(P2pConstant.SUCCESS_MSG);
        } catch (ParamsExcetion e) {
            e.printStackTrace();
            resultInfo.setCode(P2pConstant.FAILED_CODE);
            resultInfo.setMsg(e.getErrorMsg());
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setCode(P2pConstant.FAILED_CODE);
            resultInfo.setMsg(P2pConstant.FAILED_MSG);
        }
        return resultInfo;

    }

}
