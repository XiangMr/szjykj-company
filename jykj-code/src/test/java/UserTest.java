import com.alibaba.fastjson.JSON;
import com.dome.szjykjcompany.SzjykjCompanyApplication;
import com.dome.szjykjcompany.mapper.SysMenuMapper;
import com.dome.szjykjcompany.mapper.SysUserExtendMapper;
import com.dome.szjykjcompany.mapper.SysUserMapper;
import com.dome.szjykjcompany.pojo.SysMenu;
import com.dome.szjykjcompany.pojo.SysUser;
import com.dome.szjykjcompany.pojo.SysUserExtend;
import com.dome.szjykjcompany.service.IsRoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@SpringBootTest(classes = SzjykjCompanyApplication.class)
@RunWith(SpringRunner.class)
public class UserTest {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysUserExtendMapper sysUserExtendMapper;

    @Autowired
    private IsRoleService isRoleService;
    
    @Test
    public void test_01(){
        List<SysMenu> sysMenus = sysMenuMapper.QuerySysMenuList();
        HashMap<String,Object> map=new HashMap<>();
        map.put("msg","0");
        map.put("code","true");
        map.put("data",sysMenus);
        System.out.println(JSON.toJSONString(map));
    }

    @Test
    public void test_02(){
        Set<String> set = new HashSet<>();
        set.add("张三");
        set.add("李四");
        set.add("李四-1");
        set.add("李四-2");
        set.add("李四-3");
        for (String s : set) {
            System.out.println("s.getBytes() = " + s.getBytes());
        }
    }

    @Test
    public void test_03(){
        Set<String> strings = isRoleService.selectUserRoleSet(1);
        strings.forEach(System.out::println);
    }











}
