package com.shsxt.xm.api.dto;

import com.shsxt.xm.api.constant.YunTongFuConstant;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 构建支付请求参数 数据的转换类
 */
public class PayDto implements Serializable{
    private static final long serialVersionUID = 3508963379682470414L;
    //订单号
    private String outOrderNo;
    //订单名称
    private String subject;
    //订单描述信息
    private String body;
    //异步
    private String notifyUrl=YunTongFuConstant.NOTIFY_URL;
    //同步
    private String returnUrl=YunTongFuConstant.RETURN_URL;
    //商户号
    private String userSeller=YunTongFuConstant.USER_SELLER;
    //身份
    private String partner=YunTongFuConstant.PARTNER;
    //总金额
    private BigDecimal totalFee;
    //请求签名串
    private String sign;
    //安全校验码
    private String key= YunTongFuConstant.KEY;
    //支付网关地址
    private String GateWayNew =YunTongFuConstant.GATEWAY_NEW;

    public String getOutOrderNo() {
        return outOrderNo;
    }

    public void setOutOrderNo(String outOrderNo) {
        this.outOrderNo = outOrderNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getUserSeller() {
        return userSeller;
    }

    public void setUserSeller(String userSeller) {
        this.userSeller = userSeller;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getGateWayNew() {
        return GateWayNew;
    }

    public void setGateWayNew(String gateWayNew) {
        GateWayNew = gateWayNew;
    }
}
