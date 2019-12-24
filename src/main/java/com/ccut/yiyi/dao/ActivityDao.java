package com.ccut.yiyi.dao;

import com.ccut.yiyi.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName: ActivityDao
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/18 16:33
 * @version: V1.0
 */
public interface ActivityDao extends JpaRepository<Activity,String>,JpaSpecificationExecutor<Activity> {
}
