package com.shsxt.xm.api.service;

import com.shsxt.xm.api.po.User;

public interface IUserService {

    public User queryUserById(Integer id);

    public User queryUserByPhone(String phone);

    public void saveUser(String phone,String password);

    public User userLogin(String phone,String password);

    public User quickLogin(String phone);
}
