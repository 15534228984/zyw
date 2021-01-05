package com.baizhi.controller;

import com.baizhi.service.FeedbackService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;

@Controller
@RequestMapping("feedbckConrtoller")
public class FeedbackConrtoller {
    @Resource
    FeedbackService feedbackService;

    @ResponseBody
    @RequestMapping("show")
    public HashMap<String, Object> show(Integer page, Integer rows){
        return feedbackService.queryShow(page, rows);
    }

}
