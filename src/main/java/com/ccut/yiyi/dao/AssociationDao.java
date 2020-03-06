package com.ccut.yiyi.dao;

import com.ccut.yiyi.model.Association;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @ClassName: AssociationDao
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/18 16:35
 * @version: V1.0
 */
public interface AssociationDao extends JpaRepository<Association, Integer>, JpaSpecificationExecutor<Association> {
    /**
     * 通过社团类型查询此类型下的所有社团
     *
     * @param code 社团类型
     * @return 返回查询数据
     */
    List<Association> findByTypeCode(Integer code);

    /**
     * 通过学号和社团id查询社团
     *
     * @param stuCode 学号
     * @param assId   社团id
     * @return 返回数据
     */
    Association findByStuCodeAndAssId(String stuCode, Integer assId);

    /**
     * 通过学号查询此学生创建的所有社团
     *
     * @param stuCode 学号
     * @return 返回查询数据
     */
    List<Association> findByStuCode(String stuCode);

    Page<Association> findByTypeCode(Integer typeCode, Pageable pageable);
}
