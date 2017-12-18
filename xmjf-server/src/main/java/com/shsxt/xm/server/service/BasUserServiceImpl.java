package com.shsxt.xm.server.service;

import com.shsxt.xm.api.constant.P2pConstant;
import com.shsxt.xm.api.po.*;
import com.shsxt.xm.api.service.IUserService;
import com.shsxt.xm.api.utils.AssertUtil;
import com.shsxt.xm.api.utils.RandomCodesUtils;
import com.shsxt.xm.server.db.dao.*;
import com.shsxt.xm.server.utils.DateUtils;
import com.shsxt.xm.server.utils.MD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class BasUserServiceImpl implements IUserService {

    @Resource
    private UserDao userDao;

    @Resource
    private BasUserInfoDao basUserInfoDao;

    @Resource
    private BasUserSecurityDao basUserSecurityDao;

    @Resource
    private BusAccountDao busAccountDao;

    @Resource
    private BusUserIntegralDao busUserIntegralDao;

    @Resource
    private BusIncomeStatDao busIncomeStatDao;

    @Resource
    private BusUserStatDao busUserStatDao;

    @Resource
    private BasExperiencedGoldDao basExperiencedGoldDao;

    @Resource
    private SysLogDao sysLogDao;

    @Override
    public User queryUserById(Integer id) {
        return userDao.queryUserById(id);
    }

    @Override
    public User queryUserByPhone(String phone) {
        return userDao.queryUserByPhone(phone);
    }

    @Override
    public void saveUser(String phone, String password) {

        Integer userId = initUser(phone, password);

        initBasUserInfo(userId);

        initBasUserSecurity(userId);

        initBusAccount(userId);

        initBusUserIntegral(userId);

        initBusIncomeStat(userId);

        initBusUserStat(userId);

        initBasExperiencedGold(userId);

        initSysLog(userId);
    }

    @Override
    public User userLogin(String phone, String password) {
        AssertUtil.isTrue(StringUtils.isBlank(phone),"手机号不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(password),"密码不能为空");
        User user = userDao.queryUserByPhone(phone);
        AssertUtil.isTrue(null==user,"该用户不存在");
        password = MD5.toMD5(password+user.getSalt());
        AssertUtil.isTrue(!password.equals(user.getPassword()),"密码不正确");
        return user;
    }

    @Override
    public User quickLogin(String phone) {
        AssertUtil.isTrue(StringUtils.isBlank(phone), "手机号不能为空");
        User user = userDao.queryUserByPhone(phone);
        AssertUtil.isTrue(null == user, "用户不存在");
        return user;
    }

    private void initSysLog(Integer userId) {
        SysLog sysLog = new SysLog();
        sysLog.setUserId(userId);
        sysLog.setAddtime(new Date());
        sysLog.setCode("REGISTER");
        sysLog.setOperating("用户注册");
        sysLog.setType(4);
        sysLog.setResult(1);
        AssertUtil.isTrue(sysLogDao.insert(sysLog)<1,P2pConstant.FAILED_MSG);

    }

    private void initBasExperiencedGold(Integer userId) {
        BasExperiencedGold basExperiencedGold = new BasExperiencedGold();
        basExperiencedGold.setUserId(userId);
        basExperiencedGold.setAddtime(new Date());
        basExperiencedGold.setAmount(BigDecimal.valueOf(2888L));
        basExperiencedGold.setGoldName("注册体验金");
        basExperiencedGold.setRate(BigDecimal.valueOf(10));
        basExperiencedGold.setStatus(2);
        basExperiencedGold.setUsefulLife(3);
        basExperiencedGold.setWay("register");
        basExperiencedGold.setExpiredTime(DateUtils.addTime(1,new Date(),30));
        AssertUtil.isTrue(basExperiencedGoldDao.insert(basExperiencedGold)<1,P2pConstant.FAILED_MSG);
    }

    private void initBusUserStat(Integer userId) {
        BusUserStat busUserStat = new BusUserStat();
        busUserStat.setUserId(userId);
        AssertUtil.isTrue(busUserStatDao.insert(busUserStat)<1,P2pConstant.FAILED_MSG);
    }

    private void initBusIncomeStat(Integer userId) {
        BusIncomeStat busIncomeStat = new BusIncomeStat();
        busIncomeStat.setUserId(userId);
        AssertUtil.isTrue(busIncomeStatDao.insert(busIncomeStat)<1,P2pConstant.FAILED_MSG);

    }

    private void initBusUserIntegral(Integer userId) {
        BusUserIntegral busUserIntegral = new BusUserIntegral();
        busUserIntegral.setUserId(userId);
        busUserIntegral.setUsable(0);
        AssertUtil.isTrue(busUserIntegralDao.insert(busUserIntegral)<1,P2pConstant.FAILED_MSG);

    }

    private void initBusAccount(Integer userId) {
        BusAccount busAccount = new BusAccount();
        busAccount.setUserId(userId);
        busAccount.setCash(BigDecimal.ZERO);
        AssertUtil.isTrue(busAccountDao.insert(busAccount)<1,P2pConstant.FAILED_MSG);

    }

    private void initBasUserSecurity(Integer userId) {
        BasUserSecurity basUserSecurity = new BasUserSecurity();
        basUserSecurity.setUserId(userId);
        basUserSecurity.setPhoneStatus(1);
        AssertUtil.isTrue(basUserSecurityDao.insert(basUserSecurity)<1,P2pConstant.FAILED_MSG);

    }

    private void initBasUserInfo(Integer userId) {
        BasUserInfo basUserInfo = new BasUserInfo();
        basUserInfo.setUserId(userId);
        String investCode = RandomCodesUtils.createRandom(false,6);
        basUserInfo.setInviteCode(investCode);
        AssertUtil.isTrue(basUserInfoDao.insert(basUserInfo)<1,P2pConstant.FAILED_MSG);

    }

    private Integer initUser(String phone, String password) {
        AssertUtil.isTrue(StringUtils.isBlank(phone), "手机号不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(password), "密码不能为空");
        User user = queryUserByPhone(phone);
        AssertUtil.isTrue(null != user, "该手机号已注册");
        User basUser = new User();
        basUser.setAddtime(new Date());
        basUser.setMobile(phone);
        String salt = RandomCodesUtils.createRandom(false, 4);
        basUser.setSalt(salt);
        password = MD5.toMD5(password + salt);
        basUser.setPassword(password);
        basUser.setReferer("PC");
        basUser.setStatus(1);
        basUser.setType(1);
        AssertUtil.isTrue(userDao.insert(basUser)<1, P2pConstant.FAILED_MSG);
        Integer userId = basUser.getId();
        String year = new SimpleDateFormat("yyyy").format(new Date());
        basUser.setUsername("SXT_P2P" + year+userId);
        AssertUtil.isTrue(userDao.update(basUser) < 1, P2pConstant.FAILED_MSG);
        return userId;
    }


}
