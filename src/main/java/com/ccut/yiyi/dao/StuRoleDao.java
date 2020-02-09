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
    /**
     * 通过社团id删除中间表
     *
     * @param id 社团id
     */
    void deleteByAssoId(Integer id);

    /**
     * 通过学号删除中间表信息
     *
     * @param stuCode 学号
     * @param assoId  社团id
     */
    void deleteByStuCodeAndAssoId(String stuCode, Integer assoId);

    /**
     * 通过社团id查询中间表
     *
     * @param assoId 社团id
     * @return 返回查询到的中间表数据
     */
    List<StuRole> findByAssoId(Integer assoId);

    /**
     * 通过学号查询中间表数据
     *
     * @param stuCode 学号
     * @return 返回查询到的中间表数据
     */
    List<StuRole> findByStuCode(String stuCode);

    /**
     * 通过学号和社团id查询一条中间表数据
     *
     * @param assoId  社团id
     * @param stuCode 学号
     * @return 返回数据
     */
    StuRole findByAssoIdAndStuCode(Integer assoId, String stuCode);
}
