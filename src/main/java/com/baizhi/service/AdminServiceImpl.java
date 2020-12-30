package com.baizhi.service;
import com.baizhi.dao.AdminMapper;
import com.baizhi.entity.Admin;
import com.baizhi.entity.AdminExample;
import com.baizhi.entity.UserExample;
import com.baizhi.util.Md5Utils;
import com.baizhi.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
@Component
public class AdminServiceImpl implements AdminService {

    @Resource
    AdminMapper adminMapper;

    @Override
    public Admin selectOne(String username, String password) {
        //设置查询条件
        AdminExample example = new AdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        //查询数据
        Admin admin = adminMapper.selectOneByExample(example);
        if (admin==null){new RuntimeException("用户名出现错误");}
        if (admin.getPassword().equals(password)==false){new RuntimeException("密码错误");}
        return admin;
    }

    @Override
    public HashMap<String,Object>  queryAdminPage(Integer page, Integer rows) {
        //返回  page=当前页   rows=[User,User]数据    tolal=总页数   records=总条数
        HashMap<String, Object> map = new HashMap<>();
        //设置当前页
        map.put("page",page);
        //创建条件对象
        UserExample example = new UserExample();
        //创建分页对象   参数：从第几条开始，展示几条
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        //查询数据
        List<Admin> admins = adminMapper.selectByExampleAndRowBounds(example, rowBounds);
        map.put("rows",admins);
        //查询总条数
        int records = adminMapper.selectCountByExample(example);
        map.put("records",records);
        //计算总页数
        Integer tolal=records%rows==0?records/rows:records/rows+1;
        map.put("total",tolal);
        return map;
    }

    @Override
    public String add(Admin admin) {
        String uuid = UUIDUtil.getUUID();
        admin.setId(uuid);
        admin.setStatus("1");
        admin.setSalt(Md5Utils.getSalt(6));
        adminMapper.insertSelective(admin);
        //添加方法返回id
        return uuid;
    }

    @Override
    public void edit(Admin admin) {
        adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public void del(Admin admin) {
        adminMapper.deleteByPrimaryKey(admin);
    }

    @Override
    public Admin select(Admin admin) {
        Admin select = adminMapper.selectByPrimaryKey(admin);
        if(select.getStatus().equals("1")){
            select.setStatus("0");
            adminMapper.updateByPrimaryKeySelective(select);
        }else {
            select.setStatus("1");
            adminMapper.updateByPrimaryKeySelective(select);
        }
        return adminMapper.selectOne(admin);
    }
}
