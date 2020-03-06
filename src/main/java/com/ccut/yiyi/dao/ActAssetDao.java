package com.ccut.yiyi.dao;

import com.ccut.yiyi.model.ActAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @ClassName: ActAssetDao
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/2/18 12:36
 * @version: V1.0
 */
public interface ActAssetDao extends JpaRepository<ActAsset, Integer>, JpaSpecificationExecutor<ActAsset> {
    /**
     * 通过活动id查询所有租借物资中间表
     *
     * @param actId 活动id
     * @return 返回数据
     */
    List<ActAsset> findByActId(Integer actId);

    /**
     * 通过活动id删除中间表信息
     *
     * @param actId 活动id
     */
    void deleteByActId(Integer actId);
}
