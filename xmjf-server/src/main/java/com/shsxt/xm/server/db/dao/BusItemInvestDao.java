package com.shsxt.xm.server.db.dao;

import com.shsxt.xm.api.dto.InvestDto;
import com.shsxt.xm.api.po.BusItemInvest;
import com.shsxt.xm.server.base.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BusItemInvestDao extends BaseDao<BusItemInvest>{

    public Integer queryUserIsInvestNewItem(@Param("userId") Integer userId);

    public List<InvestDto> investInfo(@Param("userId") Integer userId);

}