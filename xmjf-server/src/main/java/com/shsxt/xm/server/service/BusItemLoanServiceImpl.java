package com.shsxt.xm.server.service;

import com.shsxt.xm.api.po.BusItemLoan;
import com.shsxt.xm.api.service.IBusItemLoanService;
import com.shsxt.xm.server.db.dao.BusItemLoanDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BusItemLoanServiceImpl implements IBusItemLoanService{

    @Resource
    private BusItemLoanDao busItemLoanDao;

    @Override
    public BusItemLoan queryBusItemLoanByUserId(Integer itemId) {
        return busItemLoanDao.queryBusItemLoanByUserId(itemId);
    }
}
