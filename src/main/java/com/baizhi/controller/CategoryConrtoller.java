package com.baizhi.controller;
import com.baizhi.entity.Category;
import com.baizhi.entity.User;
import com.baizhi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;

@Controller
@RequestMapping("category")
public class CategoryConrtoller {
    @Autowired
    CategoryService categoryService;

    @ResponseBody
    @RequestMapping("selectAll")
    public HashMap<String, Object> all(Integer page, Integer rows){
        return categoryService.selectAll(page, rows);
    }
    @ResponseBody
    @RequestMapping("selectAll1")
    public HashMap<String, Object> all1(Integer page, Integer rows,String rowId){
        return categoryService.selectAll1(page, rows, rowId);
    }
    @ResponseBody
    @RequestMapping("/edit")
    public String edit(Category category, String oper,String rowId){
        String id =null;
        if(oper.equals("add")){
            id = categoryService.add(category,rowId);
        }
        if(oper.equals("edit")){
            id = categoryService.edit(category);
        }
        if(oper.equals("del")){
            System.out.println(rowId+"id");
            System.out.println(category+"用户");
            categoryService.del(category,rowId);

        }
        return id;
    }


}
