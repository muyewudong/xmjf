package com.shsxt.xm.server.service;

import com.shsxt.xm.api.constant.P2pConstant;
import com.shsxt.xm.api.po.BasUserSecurity;
import com.shsxt.xm.api.service.IBasUserSecurityService;
import com.shsxt.xm.api.utils.AssertUtil;
import com.shsxt.xm.server.db.dao.BasUserSecurityDao;
import com.shsxt.xm.server.db.dao.UserDao;
import com.shsxt.xm.server.utils.MD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BasUserSecurityServiceImpl implements IBasUserSecurityService{

    @Resource
    private BasUserSecurityDao basUserSecurityDao;

    @Resource
    private UserDao userDao;

    @Override
    public BasUserSecurity queryBasUserSecurityById(Integer userId) {
        return basUserSecurityDao.queryBasUserSecurityById(userId);
    }

    @Override
    public Integer doUserAuth(Integer userId,String realName, String idCard, String password, String confirmPassword) {
        AssertUtil.isTrue(null==userId||userId==0||null==userDao.queryUserById(userId),"用户不存在！");
        AssertUtil.isTrue(StringUtils.isBlank(realName),"真是姓名不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(password)||StringUtils.isBlank(confirmPassword)||(!password.equals(confirmPassword)),"输入密码错误！");
        AssertUtil.isTrue(StringUtils.isAllLowerCase(idCard),"身份证不能为空");
        AssertUtil.isTrue(idCard.length()!=18,"身份证不合法！");
        AssertUtil.isTrue(null!=basUserSecurityDao.queryBasUserSecurityByIdCard(idCard),"身份已验证！");
        BasUserSecurity basUserSecurity = basUserSecurityDao.queryBasUserSecurityById(userId);
        basUserSecurity.setRealname(realName);
        basUserSecurity.setIdentifyCard(idCard);
        basUserSecurity.setPaymentPassword(MD5.toMD5(confirmPassword));
        basUserSecurity.setRealnameStatus(1);
        System.out.println(basUserSecurity.getRealname());
        Integer update = basUserSecurityDao.update(basUserSecurity);
        return update;
    }

    @Override
    public BasUserSecurity queryBasUserSecurityByIdCard(String idCard) {
        return basUserSecurityDao.queryBasUserSecurityByIdCard(idCard);
    }
}
