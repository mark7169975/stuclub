package com.ccut.yiyi.service.impl;

import com.ccut.yiyi.dao.StuRoleDao;
import com.ccut.yiyi.dao.StudentDao;
import com.ccut.yiyi.model.StuRole;
import com.ccut.yiyi.model.Student;
import com.ccut.yiyi.model.group.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 验证数据库和登录时的信息是否一样
 *
 * @ClassName: MyUserDetailsService
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/2/10 19:23
 * @version: V1.0
 */
@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private StuRoleDao stuRoleDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //这里可以可以通过username（登录时输入的用户名）然后到数据库中找到对应的用户信息，并构建成我们自己的UserInfo来返回
        Student byStuCode = studentDao.findByStuCode(username);
        //判断查询的数据是否为null，如果为null，则不存在此学生信息
        if (StringUtils.isEmpty(byStuCode)) {
            return null;
        } else {
            //通过学号查询此学生的所有管理数据
            List<StuRole> byStuCode1 = stuRoleDao.findByStuCodeAndRoleCodeNot(byStuCode.getStuCode(), 2002);
            //判断此学号学生是否是管理人员，如果查询数据为null，则不是管理人员
            if (byStuCode1.isEmpty()) {
                throw new BadCredentialsException("不是社团管理人员");
            } else {
                //定义一个标记为了判断是不是超级管理员，如果sign被赋值为1，则此账号为超级管理员
                AtomicReference<Integer> sign = new AtomicReference<>(0);
                byStuCode1.forEach(stuRole -> {
                    if (stuRole.getRoleCode().equals(2000)) {
                        sign.set(1);
                    }
                });
                //如果判断标记为1，则表示为超级管理员
                if (sign.get().equals(1)) {
                    return new UserInfo(byStuCode.getStuCode(), byStuCode.getStuPwd(), "ROLE_SUPERADMIN", true, true,
                            true, true);
                }
                //如果判断标记为0，则表示为普通管理员
                if (sign.get().equals(0)) {
                    return new UserInfo(byStuCode.getStuCode(), byStuCode.getStuPwd(), "ROLE_ADMIN", true, true,
                            true, true);
                }
            }
        }
        return null;
    }

}
