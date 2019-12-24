package com.ccut.yiyi.dao;

import com.ccut.yiyi.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName: AssetDao
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/18 16:35
 * @version: V1.0
 */
public interface AssetDao extends JpaRepository<Asset,String>,JpaSpecificationExecutor<Asset> {
}
