package com.baizhi.service;

import com.baizhi.entity.Admin;

import java.util.HashMap;
import java.util.List;

public interface AdminService {
    //登录
    Admin selectOne(String username, String password);
    //查所有
    HashMap<String,Object> queryAdminPage(Integer page, Integer rows);
    //添加
    String add(Admin admin);
    //更新
    void edit(Admin admin);
    //删除
    void del(Admin admin);
    //查一个
    Admin select(Admin admin);
}
