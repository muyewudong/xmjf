package com.shsxt.xm.web.controller;

import com.shsxt.xm.api.dto.BasItemDto;
import com.shsxt.xm.api.po.*;
import com.shsxt.xm.api.query.BasItemQuery;
import com.shsxt.xm.api.service.*;
import com.shsxt.xm.api.utils.PageList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("basItem")
public class BasItemController {

    @Resource
    private IBasItemService basItemService;

    @Resource
    private IBusAccountService busAccountService;

    @Resource
    private IBusItemLoanService busItemLoanService;

    @Resource
    private IBasUserSecurityService basUserSecurityService;

    @Resource
    private ISysPictureService sysPictureService;

    @RequestMapping("basItemListPage")
    public String investList(HttpServletRequest request){
        request.setAttribute("ctx",request.getContextPath());
        return "invest_list";
    }

    @RequestMapping("queryBasItemByParams")
    @ResponseBody
    public PageList queryBasItemByParams(BasItemQuery basItemQuery){
        return basItemService.queryBasItemByParams(basItemQuery);
    }

    @RequestMapping("itemDetailPage")
    public String itemDetailPage(HttpServletRequest request, ModelMap modelMap, Integer itemId){
        BasItemDto basItemDto = basItemService.queryById(itemId);
        request.getSession().setAttribute("item",basItemDto);
        request.getSession().setAttribute("ctx",request.getContextPath());
        User user = (User) request.getSession().getAttribute("userInfo");
        if(null!=user){
            Integer userId = user.getId();
            BusAccount busAccount = busAccountService.queryBusAccountById(userId);
            request.getSession().setAttribute("busAccount",busAccount);

            BasUserSecurity basUserSecurity = basUserSecurityService.queryBasUserSecurityById(userId);
            request.getSession().setAttribute("loanUser",basUserSecurity);
        }
        BusItemLoan busItemLoan = busItemLoanService.queryBusItemLoanByUserId(itemId);
        request.getSession().setAttribute("busItemLoan",busItemLoan);

        List<SysPicture> sysPicture = sysPictureService.querySysPictureByItemId(itemId);
        request.getSession().setAttribute("pics",sysPicture);
        return "details";
    }


}
