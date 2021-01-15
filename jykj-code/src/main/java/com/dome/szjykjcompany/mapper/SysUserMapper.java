package com.dome.szjykjcompany.mapper;


import com.dome.szjykjcompany.pojo.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * sysUserMapper 用户接口
 */
public interface SysUserMapper {
    public List<SysUser> selectUserAllList();

    /**
     * 用户登录
     * @param userName
     * @return
     */
    public SysUser SelectUserLogin(String userName);

    /**
     * 用户密码错误次数大于5次 临时冻结账户 10分钟
     */
    public int UpdateUserTypeError(String loginName);

    /**
     * 十分钟时间过去，下次登录自动解封
     */
    public int UpdateUserTypeSeccess(String loginName);

    /**
     * 登录成功后修改用户最后登录时间
     */
    public int UpdateUserLoginDateTime(@Param("time") Date time, @Param("loginName") String loginName);

    /**
     * 修改用户基本信息
     */
    public int updateUserEx(SysUser user);

    /**
     * 新增用户
     * @param user ->对象
     * @return ->自增的id
     */
    public int addUserByExtend(SysUser user);
}
