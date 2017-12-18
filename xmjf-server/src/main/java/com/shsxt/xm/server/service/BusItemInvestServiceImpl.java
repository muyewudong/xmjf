package com.shsxt.xm.server.service;

import com.shsxt.xm.api.constant.P2pConstant;
import com.shsxt.xm.api.dto.BasItemDto;
import com.shsxt.xm.api.dto.InvestDto;
import com.shsxt.xm.api.po.*;
import com.shsxt.xm.api.service.IBasUserSecurityService;
import com.shsxt.xm.api.service.IBusItemInvestService;
import com.shsxt.xm.api.service.IUserService;
import com.shsxt.xm.api.utils.AssertUtil;
import com.shsxt.xm.api.utils.RandomCodesUtils;
import com.shsxt.xm.server.constant.ItemStatus;
import com.shsxt.xm.server.db.dao.*;
import com.shsxt.xm.server.utils.Calculator;
import com.shsxt.xm.server.utils.MD5;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

/**
 * 用户投资方法
 */
@Service
public class BusItemInvestServiceImpl implements IBusItemInvestService{

    @Resource
    private BusUserStatDao busUserStatDao;

    @Resource
    private BasItemDao basItemDao;

    @Resource
    private IUserService userService;

    @Resource
    private BusItemInvestDao busItemInvestDao;

    @Resource
    private IBasUserSecurityService basUserSecurityService;

    @Resource
    private BusAccountDao busAccountDao;

    @Resource
    private BusAccountLogDao busAccountLogDao;

    @Resource
    private BusIncomeStatDao busIncomeStatDao;

    @Resource
    private BusUserIntegralDao busUserIntegralDao;

    @Resource
    private BusIntegralLogDao busIntegralLogDao;

    @Override
    public void addBusItemInvest(Integer userId, Integer itemId, BigDecimal amount, String businessPassword) {
        checkInvestParams(userId,itemId,amount,businessPassword);
        BusItemInvest busItemInvest = new BusItemInvest();
        busItemInvest.setActualCollectAmount(BigDecimal.ZERO);
        busItemInvest.setActualCollectInterest(BigDecimal.ZERO);
        busItemInvest.setActualCollectPrincipal(BigDecimal.ZERO);
        BasItem basItem = basItemDao.queryById(itemId);
        BigDecimal lx = Calculator.getInterest(amount, basItem);
        busItemInvest.setActualUncollectAmount(amount.add(lx));
        busItemInvest.setActualUncollectInterest(lx);
        busItemInvest.setActualUncollectPrincipal(amount);
        busItemInvest.setAdditionalRateAmount(BigDecimal.ZERO);
        busItemInvest.setAddtime(new Date());
        busItemInvest.setCollectAmount(amount.add(lx));
        busItemInvest.setCollectInterest(lx);
        busItemInvest.setCollectPrincipal(amount);
        busItemInvest.setInvestAmount(amount);
        busItemInvest.setInvestCurrent(1);
        busItemInvest.setInvestDealAmount(amount);
        String orderNo = "SXT_TZ"+ RandomCodesUtils.createRandom(false,11);
        busItemInvest.setInvestOrder(orderNo);
        busItemInvest.setInvestStatus(0);
        busItemInvest.setInvestType(1);
        busItemInvest.setItemId(itemId);
        busItemInvest.setUpdatetime(new Date());
        busItemInvest.setUserId(userId);
        AssertUtil.isTrue(busItemInvestDao.insert(busItemInvest)<1,P2pConstant.FAILED_MSG);
        BusUserStat busUserStat = busUserStatDao.queryBusUserStatByUserId(userId);
        busUserStat.setInvestCount(busUserStat.getInvestCount()+1);
        busUserStat.setInvestAmount(busUserStat.getInvestAmount().add(amount));
        AssertUtil.isTrue(busUserStatDao.update(busUserStat)<1,P2pConstant.FAILED_MSG);

        //账户金额更新
        BusAccount busAccount = busAccountDao.queryBusAccountById(userId);
        busAccount.setTotal(busAccount.getTotal().add(lx));
        busAccount.setUsable(busAccount.getUsable().add(amount.negate()));
        busAccount.setCash(busAccount.getCash().add(amount.negate()));
        busAccount.setFrozen(busAccount.getFrozen().add(amount));
        busAccount.setWait(busAccount.getWait().add(amount));
        AssertUtil.isTrue(busAccountDao.update(busAccount)<1,P2pConstant.FAILED_MSG);

        //账户日志信息更新
        BusAccountLog busAccountLog = new BusAccountLog();
        busAccountLog.setUserId(userId);
        busAccountLog.setOperType("用户投标");
        busAccountLog.setOperMoney(amount);
        busAccountLog.setBudgetType(2);
        busAccountLog.setTotal(busAccount.getTotal());
        busAccountLog.setUsable(busAccount.getUsable());
        busAccountLog.setFrozen(busAccount.getFrozen());
        busAccountLog.setWait(busAccount.getWait());
        busAccountLog.setCash(busAccount.getCash());
        busAccountLog.setRepay(busAccount.getRepay());
        busAccountLog.setRemark("用户投标成功");
        busAccountLog.setAddtime(new Date());
        AssertUtil.isTrue(busAccountLogDao.insert(busAccountLog)<1,P2pConstant.FAILED_MSG);

        //用户收益表
        BusIncomeStat busIncomeStat = busIncomeStatDao.queryBusIncomeStatByUserId(userId);
        busIncomeStat.setTotalIncome(busIncomeStat.getTotalIncome().add(lx));
        busIncomeStat.setWaitIncome(busIncomeStat.getWaitIncome().add(lx));
        AssertUtil.isTrue(busIncomeStatDao.update(busIncomeStat)<1,P2pConstant.FAILED_MSG);

        //用户积分表
        BusUserIntegral busUserIntegral = busUserIntegralDao.queryBusUserIntegralByUserId(userId);
        busUserIntegral.setUsable(busUserIntegral.getUsable()+100);
        busUserIntegral.setTotal(busUserIntegral.getTotal()+100);
        AssertUtil.isTrue(busUserIntegralDao.update(busUserIntegral)<1,P2pConstant.FAILED_MSG);

        //积分日志
        BusIntegralLog busIntegralLog = new BusIntegralLog();
        busIntegralLog.setAddtime(new Date());
        busIntegralLog.setWay("用户投标");
        busIntegralLog.setStatus(0);
        busIntegralLog.setUserId(userId);
        AssertUtil.isTrue(busIntegralLogDao.insert(busIntegralLog)<1,P2pConstant.FAILED_MSG);

        //项目更新
        basItem.setItemOngoingAccount(basItem.getItemOngoingAccount().add(amount));
        basItem.setInvestTimes(basItem.getInvestTimes()+1);
        if(basItem.getItemAccount().compareTo(basItem.getItemOngoingAccount())==0){
            basItem.setItemStatus(ItemStatus.FULL_COMPLETE);
        }
        MathContext mc = new MathContext(2, RoundingMode.HALF_DOWN);
        basItem.setItemScale(basItem.getItemOngoingAccount().divide(basItem.getItemAccount(),mc).multiply(BigDecimal.valueOf(100)));
        AssertUtil.isTrue(basItemDao.update((BasItemDto)basItem)<1,P2pConstant.FAILED_MSG);


    }

    @Override
    public Map<String,Object[]> investInfo(Integer userId) {
        Map<String,Object[]> map = new HashMap<>();
        List<InvestDto> list = busItemInvestDao.investInfo(userId);
        String[] month;
        BigDecimal[] total;
        if(!CollectionUtils.isEmpty(list)){
            month = new String[list.size()];
            total = new BigDecimal[list.size()];
            for(int i = 0;i<list.size();i++){
                InvestDto investDto = list.get(i);
                month[i] = investDto.getMonth();
                total[i] = investDto.getTotal();
            }
            map.put("data1",month);
            map.put("data2",total);
        }
        return map;
    }

    private void checkInvestParams(Integer userId, Integer itemId, BigDecimal amount, String businessPassword) {
        AssertUtil.isTrue(userId==0||null==userId||null==userService.queryUserById(userId),"用户未登录或不存在");
        BasUserSecurity basUserSecurity = basUserSecurityService.queryBasUserSecurityById(userId);
        AssertUtil.isTrue(StringUtils.isBlank(businessPassword),"交易密码非空");
        businessPassword= MD5.toMD5(businessPassword);
        AssertUtil.isTrue(!basUserSecurity.getPaymentPassword().equals(businessPassword),"交易密码错误");
        AssertUtil.isTrue(null==amount||amount.compareTo(BigDecimal.ZERO)<=0,"投资金额非法");

        BasItem basItem = basItemDao.queryById(itemId);
        AssertUtil.isTrue(null==itemId||itemId==0||null==basItem,"投资项目不存在");
        AssertUtil.isTrue(basItem.getMoveVip().equals(1),"移动端专享项目，PC端不能进行投资操作");
        AssertUtil.isTrue(!basItem.getItemStatus().equals(10),"该项目属于未开放状态");
        AssertUtil.isTrue(basItem.getItemStatus().equals(20),"项目已满标");
        BusAccount busAccount = busAccountDao.queryBusAccountById(userId);
        AssertUtil.isTrue(busAccount.getUsable().compareTo(BigDecimal.ZERO)<=0,"账号可用金额不存在");
        BigDecimal singleMinInvestAmount = basItem.getItemSingleMinInvestment();
        if(singleMinInvestAmount.compareTo(BigDecimal.ZERO)>0){
            AssertUtil.isTrue(busAccount.getUsable().compareTo(singleMinInvestAmount)<0,"账号可用金额小于当前投资项目单笔最小投资金额");
        }
        BigDecimal syAmount = basItem.getItemAccount().add(basItem.getItemOngoingAccount().negate());
        if(singleMinInvestAmount.compareTo(BigDecimal.ZERO)>0){
            AssertUtil.isTrue(syAmount.compareTo(singleMinInvestAmount)<0,"项目已满标！！！");
            AssertUtil.isTrue(amount.compareTo(singleMinInvestAmount)<0,"投资金额不能小于最小投资金额");
        }
        BigDecimal singleMaxInvestment = basItem.getItemMaxInvestment();
        if(singleMaxInvestment.compareTo(BigDecimal.ZERO)>0){
            AssertUtil.isTrue(amount.compareTo(singleMaxInvestment)>0,"投资金额超过了单笔最大投资金额");
        }

        AssertUtil.isTrue(busItemInvestDao.queryUserIsInvestNewItem(userId)>0,"不能进行重复投资操作");


    }










}
