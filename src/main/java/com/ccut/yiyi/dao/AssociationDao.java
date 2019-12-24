package com.ccut.yiyi.dao;

import com.ccut.yiyi.model.Association;
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
public interface AssociationDao extends JpaRepository<Association, String>, JpaSpecificationExecutor<Association> {
    List<Association> findByTypeCode(Integer code);
}
