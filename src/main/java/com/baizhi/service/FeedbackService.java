package com.baizhi.service;

import java.util.HashMap;

public interface FeedbackService {
    //查所有
    HashMap<String,Object> queryShow(Integer page, Integer rows);
}
