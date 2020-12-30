package com.baizhi.service;

import com.baizhi.entity.Admin;
import com.baizhi.entity.User;
import com.baizhi.po.UserOOP;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface UserService {
    //查所有
    HashMap<String,Object> queryUserPage(Integer page, Integer rows);
    //添加
    String add(User user);
    //文件上传
    void uploadUserCover(MultipartFile headImg, String id, HttpServletRequest request);
    //更新
    String edit(User user);
    //删除
    void del(User user);
    //查一个
    User select(User user);
    //查月份
    HashMap<String, Object> queryUser();
    //查询用户分布
    List<UserOOP> queryByUserPPo();
}
