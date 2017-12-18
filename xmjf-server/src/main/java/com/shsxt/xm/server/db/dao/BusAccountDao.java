package com.shsxt.xm.server.db.dao;

import com.shsxt.xm.api.po.BusAccount;
import com.shsxt.xm.server.base.BaseDao;
import com.shsxt.xm.server.providers.BusAccountProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.math.BigDecimal;
import java.util.Map;

public interface BusAccountDao extends BaseDao<BusAccount>{

    //@Select("select id,user_id as userId,total,usable,cash,frozen,wait,repay from bus_account where user_id=#{userId}")

    @SelectProvider(type= BusAccountProvider.class,method = "getQueryBusAccountByUserIdSql")
    public BusAccount queryBusAccountById(@Param("userId") Integer userId);

    public Map<String, BigDecimal> accountInfo(@Param("userId") Integer userId);


}