package com.ccut.yiyi.dao;

import com.ccut.yiyi.model.StuAsso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @ClassName: StuAssoDao
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/2/5 18:45
 * @version: V1.0
 */
public interface StuAssoDao extends JpaRepository<StuAsso, Integer>, JpaSpecificationExecutor<StuAsso> {
    /**
     * 通过社团id删除中间表
     *
     * @param id 社团id
     */
    void deleteByAssId(Integer id);

    /**
     * 通过学号和社团id删除对应的中间表数据
     *
     * @param stuCode 学号
     * @param assId   社团id
     */
    void deleteByStuCodeAndAssId(String stuCode, Integer assId);

    /**
     * 通过学号查询中间表数据
     *
     * @param stuCode 学号
     * @return 返回查询数据
     */
    List<StuAsso> findByStuCode(String stuCode);
}
