import com.alibaba.druid.util.Base64;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dome.szjykjcompany.SzjykjCompanyApplication;
import com.dome.szjykjcompany.mapper.SysMenuMapper;
import com.dome.szjykjcompany.mapper.SysRoleMapper;
import com.dome.szjykjcompany.mapper.SysUserExtendMapper;
import com.dome.szjykjcompany.mapper.SysUserMapper;
import com.dome.szjykjcompany.pojo.SysMenu;
import com.dome.szjykjcompany.pojo.SysRole;
import com.dome.szjykjcompany.pojo.SysUser;
import com.dome.szjykjcompany.pojo.SysUserExtend;
import com.dome.szjykjcompany.service.IsRoleService;
import com.dome.szjykjcompany.utils.CodecUtils;
import com.sun.jmx.snmp.internal.SnmpOutgoingRequest;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.security.provider.MD5;

import java.io.UnsupportedEncodingException;
import java.util.*;

@SpringBootTest(classes = SzjykjCompanyApplication.class)
@RunWith(SpringRunner.class)
public class UserTest {


    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

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

    @Test
    public void test_04(){
        List<SysRole> sysRoles = sysRoleMapper.QueryRoleList();
        sysRoles.forEach(System.out::println);
    }

    @Test
    public void test_05(){
        Map<String,Object> map=new HashMap<>();
        List<String> list=new ArrayList<>();
        map.put("code","0");
        map.put("msg","操作成功");
        map.put("data",new Object[]{"src","http:///"});
        String json = JSONObject.toJSONString(map);
        System.out.println("json = " + json);
    }

    @Test
    public void test_06(){
        String salt = CodecUtils.generateSalt();
        ByteSource bytes = ByteSource.Util.bytes(Base64.base64ToByteArray(salt));
        System.out.println("bytes = " + bytes);
        System.out.println("pwd = " + bytes.toString());
        String  bCryptPassword = new SimpleHash("md5","sa1234","b7a4105367fc42cdb114901140eb7d79",65).toString();
        System.out.println("bCryptPassword = " + bCryptPassword);
        //Md5Hash.toString()
    }
}
