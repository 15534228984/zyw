package com.baizhi.controller;

import com.baizhi.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;

@Controller
@RequestMapping("/log")
public class LogController {
    @Resource
    LogService service;

    @ResponseBody
    @RequestMapping("/logShow")
    public HashMap<String, Object> logShow(Integer page, Integer rows){
        return service.show(page, rows);
    }

}
