package com.ccut.yiyi.service;

import com.ccut.yiyi.model.Role;

import java.util.List;

/**
 * @ClassName: RoleService
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/23 12:41
 * @version: V1.0
 */
public interface RoleService {
    /**
     * 查询所有角色信息
     * @return
     */
    List<Role> findAll();
}
