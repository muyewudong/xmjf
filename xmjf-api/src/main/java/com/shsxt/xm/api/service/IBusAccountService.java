package com.shsxt.xm.api.service;

import com.shsxt.xm.api.dto.AccountDto;
import com.shsxt.xm.api.dto.PayDto;
import com.shsxt.xm.api.po.BusAccount;
import com.shsxt.xm.api.po.BusAccountRecharge;
import com.shsxt.xm.api.query.AccountRechargeQuery;
import com.shsxt.xm.api.utils.PageList;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IBusAccountService {

    public BusAccount queryBusAccountById(Integer userId);

    public Integer userRecharge(BusAccount busAccount);

    public PayDto buildRechargeRequestInfo(BigDecimal rechargeAmount, String bussinessPassword,Integer userId);

    public PageList queryRechargeRecodesByParams(AccountRechargeQuery accountRechargeQuery);

    public List<AccountDto> accountInfo(Integer userId);

}
