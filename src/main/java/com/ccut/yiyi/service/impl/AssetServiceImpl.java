package com.ccut.yiyi.service.impl;

import com.ccut.yiyi.dao.AssetDao;
import com.ccut.yiyi.model.Asset;
import com.ccut.yiyi.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * @ClassName: AssetServiceImpl
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/2/17 11:51
 * @version: V1.0
 */
@Service
@Transactional
public class AssetServiceImpl implements AssetService {
    @Autowired
    private AssetDao assetDao;

    @Override
    public void add(Asset asset) {
        //设置已借出为0
        asset.setAssetBorrow(0);
        //剩余数量和总数量一样
        asset.setAssetRemain(asset.getAssetNumber());
        //把信息存到数据库
        assetDao.save(asset);
    }

    @Override
    public Page<Asset> findSearch(Map searchMap, int page, int size) {
        Specification<Asset> specification = createSpecification(searchMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.Direction.DESC, "assetName");
        return assetDao.findAll(specification, pageRequest);
    }

    @Override
    public void deleteById(Integer id) {
        Optional<Asset> byId = assetDao.findById(id);
        if (byId.isPresent()) {
            if (byId.get().getAssetBorrow() != 0) {
                throw new RuntimeException("删除失败，存在借出资源");
            } else {
                assetDao.deleteById(id);
            }

        }
    }

    @Override
    public Asset findById(Integer id) {
        //通过id查询资产数据
        Optional<Asset> byId = assetDao.findById(id);
        return byId.orElse(null);
    }

    @Override
    public void update(Asset asset) {
        //如果修改资产的总数量，则需要更新剩余数量
        asset.setAssetRemain(asset.getAssetNumber() - asset.getAssetBorrow());
        System.out.println(asset);
        assetDao.save(asset);
    }

    @Override
    public List<Asset> findAll() {
        List<Asset> byAssetRemainNot = assetDao.findByAssetRemainNot(0);
        byAssetRemainNot.sort((o1, o2) -> o2.getAssetName().charAt(0)-o1.getAssetName().charAt(0));
        return byAssetRemainNot;
    }

    /**
     * 动态条件构建
     *
     * @param searchMap 查询条件
     */
    private Specification<Asset> createSpecification(Map searchMap) {

        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            // 资产名称
            if (searchMap.get("search") != null && !"".equals(searchMap.get("search"))) {
                predicateList.add(cb.like(root.get("assetName").as(String.class), "%" + searchMap.get
                        ("search") + "%"));
            }
            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

        };

    }
}
