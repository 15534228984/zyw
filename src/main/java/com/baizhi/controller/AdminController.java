package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.entity.User;
import com.baizhi.service.AdminService;
import com.baizhi.util.CreateValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.util.HashMap;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService service;
    //登录
    @RequestMapping("/login")
    public String login(String username, String password, String vcode,HttpSession session){
        try{
            //验证码判断
            String icode = (String) session.getAttribute("icode");
            if (!vcode.equals(icode))throw new RuntimeException("验证码出错");
            //用户判断
            Admin admin = service.selectOne(username,password);
            //登录成功存入session
            session.setAttribute("admin",admin);
            return "redirect:/main/main.jsp";
        }catch (Exception e){
            e.printStackTrace();
            //登录不成功错误信息存入session，并且回到login页面
            session.setAttribute("error",e.getMessage());
            return "redirect:/login/login.jsp";
        }
    }
    //验证码
    @RequestMapping("/ImageAction")
    public String execute(HttpSession session, HttpServletResponse response) throws Exception{
        //1. 调用工具类的方法获取图片上的随机数
        CreateValidateCode cvc = new CreateValidateCode();
        String icode = cvc.getCode();
        //2. 将随机数存入session作用域
        session.setAttribute("icode",icode);
        System.out.println(icode);
        //3. 向屏幕输出
        OutputStream out = response.getOutputStream();
        cvc.write(out);
        return null;
    }
    //安全退出
    @RequestMapping("/LoginOut")
    public String loginout(HttpSession session){
        session.removeAttribute("admin");
        session.invalidate();
        return "redirect:/login/login.jsp";
    }
    //查所有
    @ResponseBody
    @RequestMapping("/queryAdminPage")
    public HashMap<String,Object> queryAdminPage(Integer page, Integer rows){
        return service.queryAdminPage(page, rows);
    }
    //增删改走的
    @ResponseBody
    @RequestMapping("/edit")
    public String edit(Admin admin, String oper){
        String id =null;
        if(oper.equals("add")){
            id = service.add(admin);
        }
        if(oper.equals("edit")){
            System.out.println(admin+"修改用户");
            service.edit(admin);
        }
        if(oper.equals("del")){
            service.del(admin);
        }
        return id;
    }
    //管理员冻结修改
    @RequestMapping("freeze")
    public void  freeze(Admin admin){

        service.select(admin);
    }
}
