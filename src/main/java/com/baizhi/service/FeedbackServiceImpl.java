package com.baizhi.service;

import com.baizhi.annotation.AddCache;
import com.baizhi.dao.FeedbackMapper;
import com.baizhi.entity.Feedback;
import com.baizhi.entity.Video;
import com.baizhi.entity.VideoExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    @Resource
    FeedbackMapper feedbackMapper;


    @AddCache
    @Override
    public HashMap<String, Object> queryShow(Integer page, Integer rows) {

        HashMap<String, Object> map = new HashMap<>();
        //当前页   page
        map.put("page", page);
                        
        //总条数   records
        VideoExample example = new VideoExample();
        int records = feedbackMapper.selectCountByExample(example);
        map.put("records", records);

        //总页数   total
        //总页数=总条数/每页展示条数   有余数加一页
        Integer total = records / rows == 0 ? records / rows : records / rows + 1;
        map.put("total", total);

        //数据    rows   参数：略过几条，要几条
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Feedback> feedbacks = feedbackMapper.selectByRowBounds(new Feedback(), rowBounds);
        map.put("rows", feedbacks);

        return map;
    }
}
