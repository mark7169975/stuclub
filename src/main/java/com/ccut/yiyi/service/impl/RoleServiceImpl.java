package com.ccut.yiyi.service.impl;

import com.ccut.yiyi.dao.RoleDao;
import com.ccut.yiyi.model.Role;
import com.ccut.yiyi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: RoleServiceImpl
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/23 12:42
 * @version: V1.0
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }
}
