package com.shsxt.xm.api.service;

import com.shsxt.xm.api.dto.BasItemDto;
import com.shsxt.xm.api.po.BusAccount;
import com.shsxt.xm.api.query.BasItemQuery;
import com.shsxt.xm.api.utils.PageList;

public interface IBasItemService {

    public PageList queryBasItemByParams(BasItemQuery basItemQuery);

    public BasItemDto queryById(Integer itemId);


}
