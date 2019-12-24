package com.ccut.yiyi.dao;

import com.ccut.yiyi.model.AssociationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName: AssociationTypeDao
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/18 16:38
 * @version: V1.0
 */
public interface AssociationTypeDao extends JpaRepository<AssociationType, String>,
        JpaSpecificationExecutor<AssociationType> {
    AssociationType findByTypeCode(Integer code);
}
