package com.baizhi.service;
import com.baizhi.annotation.AddCache;
import com.baizhi.annotation.AddLog;
import com.baizhi.annotation.DelCache;
import com.baizhi.dao.CategoryMapper;
import com.baizhi.entity.Category;
import com.baizhi.entity.CategoryExample;
import com.baizhi.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
@Component
public class CategoryServiceImpl implements CategoryService {

    @Resource
    CategoryMapper categoryMapper;

    @AddCache
    @Override
    public HashMap<String,Object> selectAll(Integer page, Integer rows) {
        //返回  page=当前页   rows=[User,User]数据    tolal=总页数   records=总条数
        HashMap<String, Object> map = new HashMap<>();
        //设置当前页
        map.put("page",page);
        //创建条件对象
        CategoryExample example = new CategoryExample();
        //创建分页对象   参数：从第几条开始，展示几条
        example.createCriteria().andLevelsEqualTo(1);
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        //查询数据
        List<Category> categories1 = categoryMapper.selectByExampleAndRowBounds(example,rowBounds);
        map.put("rows",categories1);
        //查询总条数
        int records = categoryMapper.selectCountByExample(example);
        map.put("records",records);
        //计算总页数
        Integer tolal=records%rows==0?records/rows:records/rows+1;
        map.put("total",tolal);
        return map;
    }

    @Override
    public HashMap<String, Object> selectAll1(Integer page, Integer rows, String rowId) {
        //返回  page=当前页   rows=[User,User]数据    tolal=总页数   records=总条数
        HashMap<String, Object> map = new HashMap<>();
        //设置当前页
        map.put("page",page);
        //创建条件对象
        CategoryExample example = new CategoryExample();
        //创建分页对象   参数：从第几条开始，展示几条
        example.createCriteria().andParentIdEqualTo(rowId);  //修改父键为rowId
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        //查询数据
        List<Category> categories1 = categoryMapper.selectByExampleAndRowBounds(example,rowBounds);
        map.put("rows",categories1);
        //查询总条数
        int records = categoryMapper.selectCountByExample(example);
        map.put("records",records);
        //计算总页数
        Integer tolal=records%rows==0?records/rows:records/rows+1;
        map.put("total",tolal);
        return map;
    }

    @DelCache
    @AddLog("aaa")
    @Override
    public String add(Category category,String rowId) {
        if(rowId==null){
            category.setId(UUIDUtil.getUUID());
            category.setLevels(1);
            category.setParentId("");
            categoryMapper.insertSelective(category);
        }else {
            category.setId(UUIDUtil.getUUID());
            category.setLevels(2);
            category.setParentId(rowId);
            categoryMapper.insertSelective(category);
        }
        return null;
    }

    @DelCache
    @AddLog("aaa")
    @Override
    public String edit(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
        return null;
    }

    @DelCache
    @AddLog("aaa")
    @Override
    public String del(Category category,String rowId) {
        if(rowId==null){  //删除一级类别
            //创建条件对象
            CategoryExample example = new CategoryExample();
            //创建分页对象   参数：从第几条开始，展示几条
            example.createCriteria().andParentIdEqualTo(category.getId());  //修改父键为rowId
            List<Category> categories = categoryMapper.selectByExample(example);
            if(categories.size()==0){
                categoryMapper.deleteByPrimaryKey(category);
            }else {
                throw new RuntimeException("请先删除您下面的孩子");
            }
        }else {  //删除二级类别
            categoryMapper.deleteByPrimaryKey(category);
        }
        return null;
    }

}
