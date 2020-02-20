package com.ccut.yiyi.dao;

import com.ccut.yiyi.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @ClassName: AssetDao
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/18 16:35
 * @version: V1.0
 */
public interface AssetDao extends JpaRepository<Asset, Integer>, JpaSpecificationExecutor<Asset> {
    /**
     * 查询所有有资源的资产
     *
     * @param borrow 剩余数量
     * @return 返回数据
     */
    List<Asset> findByAssetRemainNot(Integer borrow);
}
