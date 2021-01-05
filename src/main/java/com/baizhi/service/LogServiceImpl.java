package com.baizhi.service;

import com.baizhi.annotation.AddCache;
import com.baizhi.annotation.DelCache;
import com.baizhi.dao.LogMapper;
import com.baizhi.entity.Log;
import com.baizhi.entity.UserExample;
import com.baizhi.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class LogServiceImpl implements LogService {

    @Resource
    LogMapper logMapper;

    @AddCache
    @Override
    public HashMap<String, Object> show(Integer page, Integer rows) {
        //返回  page=当前页   rows=[User,User]数据    tolal=总页数   records=总条数
        HashMap<String, Object> map = new HashMap<>();
        //设置当前页
        map.put("page",page);
        //创建条件对象
        UserExample example = new UserExample();
        //创建分页对象   参数：从第几条开始，展示几条
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        //查询数据
        List<Log> logs = logMapper.selectByExampleAndRowBounds(example, rowBounds);
        map.put("rows",logs);
        //查询总条数
        int records = logMapper.selectCountByExample(example);
        map.put("records",records);
        //计算总页数
        Integer tolal=records%rows==0?records/rows:records/rows+1;
        map.put("total",tolal);
        return map;
    }

    @Override
    public void add(Log log) {
        logMapper.insertSelective(log);

    }

}
