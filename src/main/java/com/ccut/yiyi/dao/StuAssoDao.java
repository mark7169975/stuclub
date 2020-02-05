package com.ccut.yiyi.dao;

import com.ccut.yiyi.model.StuAsso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName: StuAssoDao
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/2/5 18:45
 * @version: V1.0
 */
public interface StuAssoDao extends JpaRepository<StuAsso,Integer>,JpaSpecificationExecutor<StuAsso> {

}
