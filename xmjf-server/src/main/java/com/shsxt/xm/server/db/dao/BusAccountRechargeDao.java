package com.shsxt.xm.server.db.dao;

import com.shsxt.xm.api.dto.BasItemDto;
import com.shsxt.xm.api.po.BusAccountRecharge;
import com.shsxt.xm.api.query.AccountRechargeQuery;
import com.shsxt.xm.api.query.BaseQuery;
import com.shsxt.xm.server.base.BaseDao;

import java.util.List;

public interface BusAccountRechargeDao extends BaseDao<BusAccountRecharge>{

    public List<BusAccountRecharge> queryForPageByQuery(AccountRechargeQuery accountRechargeQuery);


}