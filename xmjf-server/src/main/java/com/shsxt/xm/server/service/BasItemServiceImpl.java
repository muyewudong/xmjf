package com.shsxt.xm.server.service;

import com.github.pagehelper.PageHelper;

import com.shsxt.xm.api.dto.BasItemDto;
import com.shsxt.xm.api.po.BusAccount;
import com.shsxt.xm.api.query.BasItemQuery;
import com.shsxt.xm.api.service.IBasItemService;
import com.shsxt.xm.api.utils.PageList;
import com.shsxt.xm.server.db.dao.BasItemDao;
import com.shsxt.xm.server.db.dao.BusAccountDao;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.List;

@Service
public class BasItemServiceImpl implements IBasItemService {

    @Resource
    private BasItemDao basItemDao;

    @Resource
    private BusAccountDao busAccountDao;


    @Override
    public PageList queryBasItemByParams(BasItemQuery basItemQuery) {
        PageHelper.startPage(basItemQuery.getPageNum(),basItemQuery.getPageSize());
        List<BasItemDto> basItemDtos=basItemDao.queryForPage(basItemQuery);
        return new PageList(basItemDtos);
    }

    @Override
    public BasItemDto queryById(Integer itemId) {
        return basItemDao.queryById(itemId);
    }


}

