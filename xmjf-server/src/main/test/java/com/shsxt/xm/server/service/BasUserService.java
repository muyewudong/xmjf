package com.shsxt.xm.server.service;

import com.shsxt.xm.api.service.IUserService;
import org.junit.Test;

import javax.annotation.Resource;

public class BasUserService extends TestBase{

    @Resource
    private IUserService userService;

    @Test
    public void test(){
        userService.saveUser("182121212121","123456");
    }

}
