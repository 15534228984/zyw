package com.baizhi.service;

import com.baizhi.entity.Category;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public interface CategoryService {
    //查询一级类别
    HashMap<String,Object> selectAll(Integer page, Integer rows);
    //查询二级类别
    HashMap<String,Object> selectAll1(Integer page, Integer rows,String rowId);
    //添加
    String add(Category category,String rowId);
    //修改
    String edit(Category category);
    //删除
    String del(Category category,String rowId);
}
