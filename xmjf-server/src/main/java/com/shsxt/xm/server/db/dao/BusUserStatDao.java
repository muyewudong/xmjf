package com.shsxt.xm.server.db.dao;

import com.shsxt.xm.api.po.BusUserStat;
import com.shsxt.xm.api.po.BusUserStatKey;
import com.shsxt.xm.server.base.BaseDao;
import org.apache.ibatis.annotations.Param;

public interface BusUserStatDao extends BaseDao<BusUserStat>{

    public BusUserStat queryBusUserStatByUserId(@Param("userId") Integer userId);

}