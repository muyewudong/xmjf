package com.shsxt.xm.api.service;

import com.shsxt.xm.api.po.BasUserSecurity;

public interface IBasUserSecurityService {

    public BasUserSecurity queryBasUserSecurityById(Integer userId);

    public Integer doUserAuth(Integer userId,String realName, String idCard, String password, String confirmPassword);

    public BasUserSecurity queryBasUserSecurityByIdCard(String idCard);

}
