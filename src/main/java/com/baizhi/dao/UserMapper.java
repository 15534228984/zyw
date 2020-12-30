package com.baizhi.dao;

import com.baizhi.entity.User;
import com.baizhi.entity.UserExample;
import java.util.List;

import com.baizhi.po.UserPO;
import com.baizhi.po.UserPPO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {

    List<UserPO> queryUser();

    //查询用户分布
    List<UserPPO> queryByUserPPo(String sex);

}