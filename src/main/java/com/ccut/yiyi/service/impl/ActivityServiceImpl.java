package com.ccut.yiyi.service.impl;

import com.ccut.yiyi.dao.ActAssetDao;
import com.ccut.yiyi.dao.ActivityDao;
import com.ccut.yiyi.dao.AssetDao;
import com.ccut.yiyi.dao.AssociationDao;
import com.ccut.yiyi.model.ActAsset;
import com.ccut.yiyi.model.Activity;
import com.ccut.yiyi.model.Asset;
import com.ccut.yiyi.model.Association;
import com.ccut.yiyi.model.group.ActivityGroup;
import com.ccut.yiyi.service.ActivityService;
import com.ccut.yiyi.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: ActivityServiceImpl
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/2/15 15:18
 * @version: V1.0
 */
@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityDao activityDao;
    @Autowired
    private AssetService assetService;
    @Autowired
    private AssetDao assetDao;
    @Autowired
    private ActAssetDao actAssetDao;
    @Autowired
    private AssociationDao associationDao;

    @Override
    public void add(String loginStuCode, Integer[] remain, Activity activity) {
        //设置添加活动的管理员学号
        activity.setStuCode(loginStuCode);
        //活动默认审核状态为0,0表示未审核，1表示通过，2表示未通过
        activity.setMark(0);
        Activity save = activityDao.save(activity);
        //查询所有有资源的资产
        List<Asset> all = assetService.findAll();
        //循环前端接受到的申请列表
        for (int i = 0; i < remain.length; i++) {
            //判断是否为null
            if (remain[i] != null) {
                //如果申请数量大于库存，则抛出异常
                if (all.get(i).getAssetRemain() < remain[i]) {
                    throw new RuntimeException("活动申请失败，" + all.get(i).getAssetName() + "租借数量超过剩余数量");
                } else {
                    //如果申请资产数量不大于库存
                    //查询单个资产信息
                    Asset asset = all.get(i);
                    //修改资产借出数量
                    asset.setAssetBorrow(remain[i] + asset.getAssetBorrow());
                    //修改库存剩余数量
                    asset.setAssetRemain(asset.getAssetNumber() - asset.getAssetBorrow());
                    //保存到数据库
                    assetDao.save(asset);
                    ActAsset actAsset = new ActAsset();
                    //设置活动id
                    actAsset.setActId(save.getActId());
                    //设置资产id
                    actAsset.setAssetId(asset.getAssetId());
                    //设置租借数量
                    actAsset.setNumber(remain[i]);
                    //把中间表保存到数据库
                    actAssetDao.save(actAsset);
                }
            }
        }

    }

    @Override
    public void add(String loginStuCode, Activity activity) {
        //设置添加活动的管理员学号
        activity.setStuCode(loginStuCode);
        //活动默认审核状态为0,0表示未审核，1表示通过，2表示未通过
        activity.setMark(0);
        activityDao.save(activity);
    }

    @Override
    public Page<ActivityGroup> searchApproval(Map searchMap, int page, int rows) {
        Specification<Activity> specification = createSpecification(searchMap);
        PageRequest pageRequest = PageRequest.of(page - 1, rows, Sort.Direction.ASC, "startTime");
        return activityDao.findAll(specification, pageRequest).map(activity -> {
            ActivityGroup activityGroup = new ActivityGroup();
            activityGroup.setActivity(activity);
            Optional<Association> byId = associationDao.findById(activity.getAssId());
            byId.ifPresent(activityGroup::setAssociation);
            return activityGroup;
        });
    }

    @Override
    public void changeApproval(Integer actId, Integer mark) {
        //通过活动id获取活动信息
        Optional<Activity> byId = activityDao.findById(actId);
        if (byId.isPresent()) {
            //修改审核状态
            byId.get().setMark(mark);
            activityDao.save(byId.get());
        }
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Activity> createSpecification(Map searchMap) {

        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            // 活动名称
            if (searchMap.get("actName") != null && !"".equals(searchMap.get("actName"))) {
                predicateList.add(cb.like(root.get("actName").as(String.class), "%" + searchMap.get
                        ("actName") + "%"));
            }
            // 社团id
            if (searchMap.get("assId") != null && !"".equals(searchMap.get("assId"))) {
                predicateList.add(cb.equal(root.get("assId").as(Integer.class), searchMap.get("assId")));
            }
            boolean pass = searchMap.get("pass") == null || !(Boolean) searchMap.get("pass");
            boolean noPass = searchMap.get("noPass") == null || !(Boolean) searchMap.get("noPass");
            //判断查询审核状态
            //如果通过和未通过都为null，则查询未审核
            if (pass && noPass) {
                predicateList.add(cb.equal(root.get("mark").as(Integer.class), 0));
            } else {
                if (searchMap.get("pass") != null) {
                    pass = (Boolean) searchMap.get("pass");
                } else {
                    pass = false;
                }
                if (searchMap.get("noPass") != null) {
                    noPass = (Boolean) searchMap.get("noPass");
                } else {
                    noPass = false;
                }
                //通过和未通过都查询
                if (pass && noPass) {
                    predicateList.add(cb.in(root.<Integer>get("mark")).value(1).value(2));
                } else {
                    //如果查询通过不为null
                    if (pass) {
                        predicateList.add(cb.equal(root.<Integer>get("mark"), 1));
                    }
                    //如果查询未通过不为null
                    if (noPass) {
                        predicateList.add(cb.equal(root.<Integer>get("mark"), 2));
                    }
                }
            }
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
            String format = df.format(date);
            try {
                date = df.parse(format);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            predicateList.add(cb.greaterThanOrEqualTo(root.get("startTime"), date));

            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

        };

    }
}
