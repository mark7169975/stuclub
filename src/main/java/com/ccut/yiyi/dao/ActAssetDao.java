package com.ccut.yiyi.dao;

import com.ccut.yiyi.model.ActAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName: ActAssetDao
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/2/18 12:36
 * @version: V1.0
 */
public interface ActAssetDao extends JpaRepository<ActAsset, Integer>, JpaSpecificationExecutor<ActAsset> {
}
