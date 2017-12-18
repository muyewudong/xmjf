package com.shsxt.xm.server.service;

import com.github.pagehelper.PageHelper;
import com.shsxt.xm.api.constant.P2pConstant;
import com.shsxt.xm.api.dto.AccountDto;
import com.shsxt.xm.api.dto.BasItemDto;
import com.shsxt.xm.api.dto.PayDto;
import com.shsxt.xm.api.po.BasUserSecurity;
import com.shsxt.xm.api.po.BusAccount;
import com.shsxt.xm.api.po.BusAccountRecharge;
import com.shsxt.xm.api.po.User;
import com.shsxt.xm.api.query.AccountRechargeQuery;
import com.shsxt.xm.api.service.IBasUserSecurityService;
import com.shsxt.xm.api.service.IBusAccountService;
import com.shsxt.xm.api.service.IUserService;
import com.shsxt.xm.api.utils.AssertUtil;
import com.shsxt.xm.api.utils.PageList;
import com.shsxt.xm.server.db.dao.BusAccountDao;
import com.shsxt.xm.server.db.dao.BusAccountRechargeDao;
import com.shsxt.xm.server.db.dao.UserDao;
import com.shsxt.xm.server.utils.MD5;
import com.shsxt.xm.server.utils.Md5Util;
import com.shsxt.xm.server.utils.ShanPayUtil;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.nio.cs.ext.MacDingbat;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.*;

@Service
public class BusAccountServiceImpl implements IBusAccountService{

    @Resource
    private BusAccountDao busAccountDao;

    @Resource
    private IUserService userService;

    @Resource
    private IBasUserSecurityService basUserSecurityService;

    @Resource
    private UserDao userDao;

    @Resource
    private BusAccountRechargeDao busAccountRechargeDao;

    @Override
    public BusAccount queryBusAccountById(Integer userId) {
        return busAccountDao.queryBusAccountById(userId);
    }

    @Override
    public Integer userRecharge(BusAccount busAccount) {
        AssertUtil.isTrue(null==busAccount,"用户不存在");
        Integer update = busAccountDao.update(busAccount);
        return update;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PayDto buildRechargeRequestInfo(BigDecimal rechargeAmount, String bussinessPassword, Integer userId) {
        checkParams(rechargeAmount,bussinessPassword,userId);
        /**
         * 构建支付请求参数信息
         */
        BusAccountRecharge busAccountRecharge = new BusAccountRecharge();
        busAccountRecharge.setAddtime(new Date());
        busAccountRecharge.setFeeAmount(BigDecimal.ZERO);
        String orderNo = com.shsxt.xm.server.utils.StringUtils.getOrderNo();
        busAccountRecharge.setOrderNo(orderNo);
        busAccountRecharge.setRechargeAmount(rechargeAmount);
        busAccountRecharge.setFeeRate(BigDecimal.ZERO);
        busAccountRecharge.setRemark("PC端用户充值");
        busAccountRecharge.setResource("PC端用户充值");
        busAccountRecharge.setStatus(2);
        busAccountRecharge.setType(3);
        busAccountRecharge.setUserId(userId);
        AssertUtil.isTrue(busAccountRechargeDao.insert(busAccountRecharge)<1, P2pConstant.FAILED_MSG);
        PayDto payDto = new PayDto();
        payDto.setBody("PC端用户充值操作");
        payDto.setOutOrderNo(orderNo);
        payDto.setSubject("PC端用户充值操作");
        payDto.setTotalFee(rechargeAmount);
        String Md5Sign = buildMD5Sign(payDto);
        payDto.setSign(Md5Sign);
        return payDto;
    }

    @Override
    public PageList queryRechargeRecodesByParams(AccountRechargeQuery accountRechargeQuery) {
        PageHelper.startPage(accountRechargeQuery.getPageNum(),accountRechargeQuery.getPageSize());
        List<BusAccountRecharge> busAccountRecharge = busAccountRechargeDao.queryForPageByQuery(accountRechargeQuery);
        return new PageList(busAccountRecharge);
    }

    @Override
    public List<AccountDto> accountInfo(Integer userId) {
        Map<String, BigDecimal> map = busAccountDao.accountInfo(userId);
        List<AccountDto> list = new ArrayList<AccountDto>();
        if(null!=map&&!map.isEmpty()){
            for(Map.Entry<String,BigDecimal> entry : map.entrySet()){
               AccountDto accountDto = new AccountDto();
               accountDto.setName(entry.getKey());
               accountDto.setY(entry.getValue());
               list.add(accountDto);
            }
        }
        return list;
    }


    /**
     * MD5加密
     * @param payDto
     * @return
     */
    private String buildMD5Sign(PayDto payDto) {
        StringBuffer arg = new StringBuffer();
        if(!StringUtils.isBlank(payDto.getBody())){
            arg.append("body="+payDto.getBody()+"&");
        }
        arg.append("notify_url="+payDto.getNotifyUrl()+"&");
        arg.append("out_order_no="+payDto.getOutOrderNo()+"&");
        arg.append("partner="+payDto.getPartner()+"&");
        arg.append("return_url="+payDto.getReturnUrl()+"&");
        arg.append("subject="+payDto.getSubject()+"&");
        arg.append("total_fee="+payDto.getTotalFee().toString()+"&");
        arg.append("user_seller="+payDto.getUserSeller());
        String tempSign= StringEscapeUtils.unescapeJava(arg.toString());
        Md5Util md5Util=new Md5Util();
        return md5Util.encode(tempSign+payDto.getKey(),"");
    }

    /**
     * 参数校验
     * @param rechargeAmount
     * @param bussinessPassword
     * @param userId
     */
    private void checkParams(BigDecimal rechargeAmount, String bussinessPassword, Integer userId) {
        AssertUtil.isTrue(rechargeAmount.compareTo(BigDecimal.ZERO)<=0,"充值金额非法");
        BasUserSecurity basUserSecurity = basUserSecurityService.queryBasUserSecurityById(userId);
        AssertUtil.isTrue(null==basUserSecurity,"用户未登录");
        AssertUtil.isTrue(StringUtils.isBlank(bussinessPassword),"交易密码不能为空");
        bussinessPassword = MD5.toMD5(bussinessPassword);
        AssertUtil.isTrue(!bussinessPassword.equals(basUserSecurity.getPaymentPassword()),"交易密码错误");
    }


}
