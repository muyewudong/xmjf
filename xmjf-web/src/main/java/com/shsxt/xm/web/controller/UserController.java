package com.shsxt.xm.web.controller;


import com.shsxt.xm.api.constant.P2pConstant;
import com.shsxt.xm.api.exceptions.ParamsExcetion;
import com.shsxt.xm.api.model.ResultInfo;
import com.shsxt.xm.api.po.BasUserSecurity;
import com.shsxt.xm.api.po.User;
import com.shsxt.xm.api.service.IBasUserSecurityService;
import com.shsxt.xm.api.service.IUserService;
import com.shsxt.xm.api.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("user")
public class UserController {

    @Resource
    private IUserService userService;

    @Resource
    private IBasUserSecurityService basUserSecurityService;

    @ResponseBody
    @RequestMapping("queryUserById")
    public User queryUserById(Integer id) {
        return userService.queryUserById(id);
    }

    @ResponseBody
    @RequestMapping("register")
    public ResultInfo userRegister(String phone, String picCode, String code, String password, HttpSession session){
        ResultInfo resultInfo = new ResultInfo();
        String sessionPicCode = (String) session.getAttribute(P2pConstant.PICTURE_VERIFY_KEY);
        if(StringUtils.isBlank(sessionPicCode)){
            resultInfo.setCode(P2pConstant.FAILED_CODE);
            resultInfo.setMsg("验证码已失效");
            return resultInfo;
        }
        if(!picCode.equals(sessionPicCode)){
            resultInfo.setMsg("验证码不匹配");
            resultInfo.setCode(P2pConstant.FAILED_CODE);
            return resultInfo;
        }
        //取出发送验证码的时间
        Date sessionTime = (Date) session.getAttribute(P2pConstant.PHONE_VERIFY_CODE_EXPIRE_TIME + phone);
        if(null==sessionTime){
            resultInfo.setMsg("手机验证码已失效");
            resultInfo.setCode(P2pConstant.FAILED_CODE);
            return resultInfo;
        }
        Date currTime = new Date();
        long time = (currTime.getTime()-sessionTime.getTime())/1000;
        if(time>180){
            resultInfo.setMsg("手机验证码已失效");
            resultInfo.setCode(P2pConstant.FAILED_CODE);
            return resultInfo;
        }
        String sessionCode = (String) session.getAttribute(P2pConstant.PHONE_VERIFY_CODE+phone);
        if(!sessionCode.equals(code)){
            resultInfo.setMsg("手机验证码不正确");
            resultInfo.setCode(P2pConstant.FAILED_CODE);
            return resultInfo;
        }
        try {
            userService.saveUser(phone,password);
            resultInfo.setCode(P2pConstant.SUCCESS_CODE);
            resultInfo.setMsg(P2pConstant.SUCCESS_MSG);
            session.removeAttribute(P2pConstant.PICTURE_VERIFY_KEY);
            session.removeAttribute(P2pConstant.PHONE_VERIFY_CODE_EXPIRE_TIME+phone);
            session.removeAttribute(P2pConstant.PHONE_VERIFY_CODE+phone);
        } catch (ParamsExcetion e) {
            e.printStackTrace();
            resultInfo.setMsg(e.getErrorMsg());
            resultInfo.setCode(P2pConstant.FAILED_CODE);
        } catch (Exception e){
            resultInfo.setMsg(P2pConstant.FAILED_MSG);
            resultInfo.setCode(P2pConstant.FAILED_CODE);
        }
        return resultInfo;
    }


    @RequestMapping("userLogin")
    @ResponseBody
    public ResultInfo userLogin(String phone,String password,HttpSession session){
        ResultInfo resultInfo = new ResultInfo();
        try {
            User user = userService.userLogin(phone, password);
            resultInfo.setCode(P2pConstant.SUCCESS_CODE);
            session.setAttribute("userInfo",user);
        } catch (ParamsExcetion e) {
            e.printStackTrace();
            resultInfo.setCode(P2pConstant.FAILED_CODE);
            resultInfo.setMsg(e.getErrorMsg());
        }catch (Exception e) {
            e.printStackTrace();
            resultInfo.setCode(P2pConstant.FAILED_CODE);
            resultInfo.setMsg(P2pConstant.FAILED_MSG);
        }
        return resultInfo;
    }


    @RequestMapping("quickLogin")
    @ResponseBody
    public ResultInfo quickLogin(String phone,String picCode,String code,HttpSession session){
        ResultInfo resultInfo = new ResultInfo();
        String sessionPicCode = (String) session.getAttribute(P2pConstant.PICTURE_VERIFY_KEY);
        if(StringUtils.isBlank(sessionPicCode)){
            resultInfo.setCode(P2pConstant.FAILED_CODE);
            resultInfo.setMsg("验证码已失效");
            return resultInfo;
        }
        if(!picCode.equals(sessionPicCode)){
            resultInfo.setCode(P2pConstant.FAILED_CODE);
            resultInfo.setMsg("验证码不匹配");
            return  resultInfo;
        }

        Date currTime = new Date();
        Date sessionTime = (Date) session.getAttribute(P2pConstant.PHONE_VERIFY_CODE_EXPIRE_TIME + phone);
        if(null==sessionTime){
           resultInfo.setCode(P2pConstant.FAILED_CODE);
           resultInfo.setMsg("手机验证码已失效!");
           return  resultInfo;
        }

        long time = (currTime.getTime() - sessionTime.getTime()) / 1000;
        if(time>180){
        resultInfo.setCode(P2pConstant.FAILED_CODE);
        resultInfo.setMsg("验证码已失效！");
        return  resultInfo;
        }

        String sessionCode = (String) session.getAttribute(P2pConstant.PHONE_VERIFY_CODE + phone);
        AssertUtil.isTrue(null==sessionCode,"验证码已失效");
        if(!sessionCode.equals(code)){
            resultInfo.setCode(P2pConstant.FAILED_CODE);
            resultInfo.setMsg("手机验证码不匹配");
            return  resultInfo;
        }
        try {
            User user = userService.quickLogin(phone);
            if(null==user){
                resultInfo.setCode(P2pConstant.FAILED_CODE);
                resultInfo.setMsg("用户名不存在");
            }
            resultInfo.setCode(P2pConstant.SUCCESS_CODE);
            session.setAttribute("userInfo",user);
        } catch (ParamsExcetion e) {
            e.printStackTrace();
            resultInfo.setCode(P2pConstant.FAILED_CODE);
            resultInfo.setMsg(e.getErrorMsg());
        }catch (Exception e) {
            e.printStackTrace();
            resultInfo.setCode(P2pConstant.FAILED_CODE);
            resultInfo.setMsg(P2pConstant.FAILED_MSG);
        }
        return  resultInfo;
    }


    @RequestMapping("exit")
    public String exit(HttpServletRequest request){
        request.removeAttribute("userInfo");
        request.setAttribute("ctx",request.getContextPath());
        return "login";
    }


    @RequestMapping("userAuthCheck")
    @ResponseBody
    public ResultInfo userAuthCheck(HttpServletRequest request){
        request.getSession().setAttribute("ctx",request.getContextPath());
        ResultInfo resultInfo = new ResultInfo();
        User user = (User) request.getSession().getAttribute("userInfo");
        Integer userId = user.getId();
        BasUserSecurity basUserSecurity = basUserSecurityService.queryBasUserSecurityById(userId);
        Integer status = basUserSecurity.getRealnameStatus();
        if(status==0){
            resultInfo.setCode(300);
            resultInfo.setMsg("该账号未认证！");
        }
        if(status==1){
            resultInfo.setCode(200);
            resultInfo.setMsg("该账号已通过认证！");
        }
        if(status==2){
            resultInfo.setCode(302);
            resultInfo.setMsg("认证申请已提交！");
        }
        return resultInfo;
    }

    @RequestMapping("auth")
    public String auth(HttpServletRequest request){
        request.getSession().setAttribute("ctx",request.getContextPath());
        return "user/auth";
    }


    @RequestMapping("userAuth")
    @ResponseBody
    public ResultInfo doUserAuth(HttpServletRequest request,String realName,String idCard,String password,String confirmPassword){
        request.getSession().setAttribute("ctx",request.getContextPath());
        ResultInfo resultInfo = new ResultInfo();
        User user = (User) request.getSession().getAttribute("userInfo");

        try {
            Integer code = basUserSecurityService.doUserAuth(user.getId(), realName, idCard, password, confirmPassword);
            if(code==1){
                resultInfo.setMsg(P2pConstant.SUCCESS_MSG);
                resultInfo.setCode(P2pConstant.SUCCESS_CODE);
            }else {
                resultInfo.setMsg(P2pConstant.FAILED_MSG);
                resultInfo.setCode(P2pConstant.FAILED_CODE);
            }
        } catch (ParamsExcetion e) {
            e.printStackTrace();
            resultInfo.setMsg(e.getErrorMsg());
            resultInfo.setCode(P2pConstant.FAILED_CODE);
        }catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMsg(P2pConstant.FAILED_MSG);
            resultInfo.setCode(P2pConstant.FAILED_CODE);
        }
        return resultInfo;
    }


}
