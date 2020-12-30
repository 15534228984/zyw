package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.po.UserOOP;
import com.baizhi.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @ResponseBody
    @RequestMapping("/queryUserPage")
    public HashMap<String, Object> queryUserPage(Integer page, Integer rows){
        return userService.queryUserPage(page, rows);
    }


    @ResponseBody
    @RequestMapping("/edit")
    public String edit(User user,String oper){
        String id =null;
        if(oper.equals("add")){
             id = userService.add(user);
        }
        if(oper.equals("edit")){
           id = userService.edit(user);
        }
        if(oper.equals("del")){
            userService.del(user);
        }
        return id;
    }
    @ResponseBody
    @RequestMapping("/uploadUserCover")
    public void uploadUserCover(MultipartFile headImg,String id, HttpServletRequest request){
        userService.uploadUserCover(headImg, id, request);
    }
    //用户员冻结修改
    @RequestMapping("freeze")
    public void  freeze(User user){

        userService.select(user);
    }

    @ResponseBody
    @RequestMapping("queryUser")
    public  HashMap<String, Object> queryUser(){
        return userService.queryUser();
    }
    @ResponseBody
    @RequestMapping("getUserChina")
    public List<UserOOP> getUserChina(){
        return userService.queryByUserPPo();
    }

}
