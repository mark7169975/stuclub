package com.ccut.yiyi.dao;

import com.ccut.yiyi.model.StuRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @ClassName: StuRoleDao
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/2/5 18:46
 * @version: V1.0
 */
public interface StuRoleDao extends JpaRepository<StuRole, Integer>, JpaSpecificationExecutor<StuRole> {
    void deleteByAssoId(Integer id);

    List<StuRole> findByAssoId(Integer assoId);
    StuRole findByAssoIdAndStuCode(Integer assoId,String stuCode);
}
