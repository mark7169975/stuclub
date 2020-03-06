package com.ccut.yiyi.service.impl;

import com.ccut.yiyi.dao.ActAssetDao;
import com.ccut.yiyi.dao.ActivityDao;
import com.ccut.yiyi.dao.AssetDao;
import com.ccut.yiyi.dao.AssociationDao;
import com.ccut.yiyi.model.ActAsset;
import com.ccut.yiyi.model.Activity;
import com.ccut.yiyi.model.Asset;
import com.ccut.yiyi.model.Association;
import com.ccut.yiyi.model.group.AssetGroup;
import com.ccut.yiyi.model.group.AssetLeaseInfo;
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
    @Autowired
    private ActivityDao activityDao;
    @Autowired
    private AssociationDao associationDao;
    @Autowired
    private ActAssetDao actAssetDao;

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
    public Page<AssetGroup> searchReturn(Map searchMap, int page, int size) {
        Specification<Activity> specification = createSpecificationReturn(searchMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.Direction.DESC, "startTime");
        return activityDao.findAll(specification, pageRequest).map(activity -> {
            AssetGroup assetGroup = new AssetGroup();
            assetGroup.setActivity(activity);
            Optional<Association> byId = associationDao.findById(activity.getAssId());
            byId.ifPresent(assetGroup::setAssociation);
            List<ActAsset> byActId = actAssetDao.findByActId(activity.getActId());
            ArrayList<AssetLeaseInfo> assetLeaseInfos = new ArrayList<>();
            byActId.forEach(actAsset -> {
                Optional<Asset> byId1 = assetDao.findById(actAsset.getAssetId());
                AssetLeaseInfo assetLeaseInfo = new AssetLeaseInfo();
                if (byId1.isPresent()) {
                    assetLeaseInfo.setName(byId1.get().getAssetName());
                    assetLeaseInfo.setUnit(byId1.get().getAssetUnit());
                    assetLeaseInfo.setNumber(actAsset.getNumber());
                }
                assetLeaseInfos.add(assetLeaseInfo);
            });
            assetGroup.setAssetLeaseInfoList(assetLeaseInfos);
            return assetGroup;
        });
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
        byAssetRemainNot.sort((o1, o2) -> o2.getAssetName().charAt(0) - o1.getAssetName().charAt(0));
        return byAssetRemainNot;
    }

    @Override
    public void assetReturn(Integer actId) {
        actAssetDao.findByActId(actId).forEach(actAsset -> {
            //获取资产信息
            Optional<Asset> byId1 = assetDao.findById(actAsset.getAssetId());
            if (byId1.isPresent()) {
                //更新数据
                byId1.get().setAssetBorrow(byId1.get().getAssetBorrow() - actAsset.getNumber());
                byId1.get().setAssetRemain(byId1.get().getAssetRemain() + actAsset.getNumber());
                assetDao.save(byId1.get());
            }
        });
        //删除中间表信息
        actAssetDao.deleteByActId(actId);
        //修改活动租借信息
        Optional<Activity> byId = activityDao.findById(actId);
        if (byId.isPresent()) {
            byId.get().setAssetSign(0);
            activityDao.save(byId.get());
        }
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

    /**
     * 归还资产动态条件构建
     *
     * @param searchMap 查询条件
     */
    private Specification<Activity> createSpecificationReturn(Map searchMap) {

        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            //通过审核的活动
            predicateList.add(cb.equal(root.get("mark").as(Integer.class), 1));
            //租借资产的活动
            predicateList.add(cb.equal(root.get("assetSign").as(Integer.class), 1));

            // 活动名称
            if (searchMap.get("search") != null && !"".equals(searchMap.get("search"))) {
                predicateList.add(cb.like(root.get("actName").as(String.class), "%" + searchMap.get
                        ("search") + "%"));
            }

            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

        };

    }
}
