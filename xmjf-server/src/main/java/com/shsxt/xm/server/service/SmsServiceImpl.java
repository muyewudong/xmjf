package com.shsxt.xm.server.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.StringUtil;
import com.shsxt.xm.api.po.User;
import com.shsxt.xm.api.service.ISmsService;
import com.shsxt.xm.api.service.IUserService;
import com.shsxt.xm.api.utils.AssertUtil;
import com.shsxt.xm.server.SmsType;
import com.shsxt.xm.server.constant.TaobaoConstant;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcFlowChargeProvinceRequest;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@Service
public class SmsServiceImpl implements ISmsService {

    @Resource
    private IUserService userService;

    @Override
    public void sendPhoneSms(String phone, String code, Integer type) {
        AssertUtil.isTrue(StringUtils.isBlank(phone), "手机号非法");
        /*正则判断手机号非法*/
        AssertUtil.isTrue(StringUtils.isBlank(code), "手机号验证码");
        AssertUtil.isTrue(null == type, "短信验证码类型不匹配");
        AssertUtil.isTrue(!type.equals(SmsType.REGISTER.getType()) && !type.equals(SmsType.NOTIFY.getType()), "类型不匹配");
        if (type.equals(SmsType.REGISTER.getType())) {
            User user = userService.queryUserByPhone(phone);
            AssertUtil.isTrue(null != user, "改手机号已注册");
            //doSend(phone, code,TaobaoConstant.SMSTEMPLATECODE_RSGISTER);
        }
        if (type.equals(SmsType.NOTIFY.getType())) {
            //doSend(phone, code,TaobaoConstant.SMSTEMPLATECODE_LOGIN);
        }
    }


    public void doSend(String phone, String code,String templateCode) {
        TaobaoClient client = new DefaultTaobaoClient(TaobaoConstant.SERVER_URL, TaobaoConstant.APP_KEY, TaobaoConstant.APP_SECRET);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("");
        req.setSmsType(TaobaoConstant.SMS_TYPE);
        req.setSmsFreeSignName(TaobaoConstant.SMSFREESIGNNAME);
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", code);
        req.setSmsParamString(JSON.toJSONString(map));
        req.setRecNum(phone);
        req.setSmsTemplateCode(templateCode);
        AlibabaAliqinFcSmsNumSendResponse rsp = null;
        try {
            rsp = client.execute(req);
            AssertUtil.isTrue(!rsp.isSuccess(),"短信发送失败，请稍后再试");
        } catch (ApiException e) {
            e.printStackTrace();
        }

    }


}
