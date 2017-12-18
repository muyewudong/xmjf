package com.shsxt.xm.api.service;

import com.shsxt.xm.api.dto.InvestDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IBusItemInvestService {

    public void addBusItemInvest(Integer userId, Integer itemId, BigDecimal amount,String businessPassword);

    public Map<String,Object[]> investInfo(Integer userId);


}
