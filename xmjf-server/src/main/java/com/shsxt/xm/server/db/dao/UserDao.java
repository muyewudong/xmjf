package com.shsxt.xm.server.db.dao;

import com.shsxt.xm.api.po.User;
import com.shsxt.xm.server.base.BaseDao;
import org.apache.ibatis.annotations.Param;

public interface UserDao extends BaseDao<User>{

    public User queryUserById(@Param("id") Integer id);

    public User queryUserByPhone(@Param("mobile") String phone);
}
