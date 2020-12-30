package com.baizhi.service;

import com.baizhi.entity.Log;
import java.util.HashMap;

public interface LogService {
    //查所有
    HashMap<String,Object> show(Integer page, Integer rows);
    //添加
    void add(Log log);
}
