package com.baizhi.app;

import com.aliyuncs.exceptions.ClientException;
import com.baizhi.commo.CommoResult;
import com.baizhi.po.VideoVO;
import com.baizhi.service.VideoService;
import com.baizhi.util.AliyunSendMsgUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author:xiaotao
 * @time 2020/12/26-14:48
 */
@RestController
@RequestMapping("app")
public class InterfaceController {
    @Resource
    VideoService videoService;

    @RequestMapping("getPhoneCode")
    public HashMap<String,Object> getPhoneCode(String phone){
        HashMap<String,Object> map=new HashMap<String,Object>();
        String message=null;
        try {
            String random= AliyunSendMsgUtil.getRandom(6);
            message=AliyunSendMsgUtil.sendPhoneCode(phone,random);

            map.put("status","100");
            map.put("date",phone);
        }catch (ClientException e){
            e.printStackTrace();
            map.put("status","104");
            map.put("date",null);
        }
        map.put("message",message);
        return map;
    }


    @RequestMapping("queryByReleaseTime")
    public CommoResult queryBuReleaseTime(){
        try {
            List<VideoVO> videoPOS = videoService.queryByReleaseTime();
            return new CommoResult().success("查询成功","100",videoPOS);
        }catch (Exception e) {
            e.printStackTrace();
            return new CommoResult().filed("104",null);
        }
    }
}
