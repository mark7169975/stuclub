package com.ccut.yiyi.service;

import com.ccut.yiyi.model.Asset;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: AssetService
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/2/17 11:50
 * @version: V1.0
 */
public interface AssetService {

    /**
     * 添加资产信息
     *
     * @param asset 资产信息
     */
    void add(Asset asset);

    /**
     * 分页查询
     *
     * @param searchMap 查询条件
     * @param page      页码
     * @param size      条数
     * @return 返回数据
     */
    Page<Asset> findSearch(Map searchMap, int page, int size);

    /**
     * 通过资产id删除资产
     *
     * @param id 资产id
     */
    void deleteById(Integer id);

    /**
     * 通过id查询一个资产实体信息
     *
     * @param id 资产id
     * @return 返回数据
     */
    Asset findById(Integer id);

    /**
     * 修改资产信息
     *
     * @param asset 资产信息
     */
    void update(Asset asset);

    /**
     * 查询所有有资源的资产
     * @return 返回数据
     */
    List<Asset> findAll();
}
