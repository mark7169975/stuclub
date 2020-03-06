package com.ccut.yiyi.dao;

import com.ccut.yiyi.model.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @ClassName: ActivityDao
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/18 16:33
 * @version: V1.0
 */
public interface ActivityDao extends JpaRepository<Activity, Integer>, JpaSpecificationExecutor<Activity> {
    /**
     * 通过审核条件查询数据
     *
     * @param mark 审核条件
     * @return 返回数据
     */
    List<Activity> findByMark(Integer mark);

    /**
     * 通过审核条件查询数据
     *
     * @param mark     审核条件
     * @param pageable 分页条件
     * @return 返回数据
     */
    Page<Activity> findByMark(Integer mark, Pageable pageable);
}
