package com.ccut.yiyi.dao;

import com.ccut.yiyi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName: RoleDao
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/18 16:37
 * @version: V1.0
 */
public interface RoleDao extends JpaRepository<Role,Integer>,JpaSpecificationExecutor<Role> {
}
