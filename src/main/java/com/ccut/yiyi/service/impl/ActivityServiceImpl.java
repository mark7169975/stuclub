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
import com.ccut.yiyi.service.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
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
    @Autowired
    private AssociationService associationService;

    @Override
    public void add(String loginStuCode, Integer[] remain, Activity activity) {
        //判断是否使用资产
        int sign = 0;
        for (int i = 0; i < remain.length; i++) {
            if (remain[i] == null) {
                sign++;
            }
        }
        //如果相等则未使用资产
        if (sign == remain.length) {
            activity.setAssetSign(0);
        } else {
            activity.setAssetSign(1);
        }
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
        //未使用资产设置标记
        activity.setAssetSign(0);
        //活动默认审核状态为0,0表示未审核，1表示通过，2表示未通过
        activity.setMark(0);
        activityDao.save(activity);
    }

    @Override
    public Page<ActivityGroup> searchApproval(Map searchMap, int page, int rows) {
        //定义标记 在活动审核页面
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

    @Override
    public Page<ActivityGroup> findSearch(Map searchMap, int page, int rows, String stuCode, String role) {
        //定义标记 在活动列表页面
        List<Association> byStuCodeAndRole = associationService.findByStuCodeAndRole(stuCode, role);
        if (!byStuCodeAndRole.isEmpty()) {
            ArrayList<Integer> integers = new ArrayList<>();
            //把查询出来的社团信息的社团id放在list集合里
            byStuCodeAndRole.forEach(association -> integers.add(association.getAssId()));
            //调用动态构建
            Specification<Activity> specification = createSpecification(searchMap, integers);
            PageRequest pageRequest = PageRequest.of(page - 1, rows, Sort.Direction.DESC, "startTime");
            return activityDao.findAll(specification, pageRequest).map(activity -> {
                ActivityGroup activityGroup = new ActivityGroup();
                activityGroup.setActivity(activity);
                Optional<Association> byId = associationDao.findById(activity.getAssId());
                byId.ifPresent(activityGroup::setAssociation);
                return activityGroup;
            });
        }
        return null;
    }

    @Override
    public Page<ActivityGroup> findSearch(int page, int rows) {
        PageRequest pageRequest = PageRequest.of(page - 1, rows, Sort.Direction.DESC, "startTime");
        return activityDao.findByMark(1, pageRequest).map(activity -> {
            ActivityGroup activityGroup = new ActivityGroup();
            activityGroup.setActivity(activity);
            Optional<Association> byId = associationDao.findById(activity.getAssId());
            byId.ifPresent(activityGroup::setAssociation);
            return activityGroup;
        });

    }

    @Override
    public List<ActivityGroup> findAll() {
        List<Activity> byMark = activityDao.findByMark(1);
        byMark.sort((o1, o2) -> Math.toIntExact((o2.getStartTime().getTime() - o1.getStartTime().getTime())));
        ArrayList<ActivityGroup> activityGroups = new ArrayList<>();
        if (byMark.size() > 6) {
            List<Activity> activities = byMark.subList(0, 6);
            activities.forEach(activity -> {
                ActivityGroup activityGroup = new ActivityGroup();
                activityGroup.setActivity(activity);
                Optional<Association> byId = associationDao.findById(activity.getAssId());
                byId.ifPresent(activityGroup::setAssociation);
                activityGroups.add(activityGroup);
            });
            return activityGroups;
        } else {
            byMark.forEach(activity -> {
                ActivityGroup activityGroup = new ActivityGroup();
                activityGroup.setActivity(activity);
                Optional<Association> byId = associationDao.findById(activity.getAssId());
                byId.ifPresent(activityGroup::setAssociation);
                activityGroups.add(activityGroup);
            });
            return activityGroups;
        }

    }

    @Override
    public void reapply(Integer actId) {
        Optional<Activity> byId = activityDao.findById(actId);
        if (byId.isPresent()) {
            byId.get().setMark(0);
            activityDao.save(byId.get());
        }
    }

    @Override
    public ActivityGroup findOne(Integer id) {

        Optional<Activity> byId = activityDao.findById(id);
        ActivityGroup activityGroup = new ActivityGroup();
        if (byId.isPresent()) {
            activityGroup.setActivity(byId.get());
            Optional<Association> byId1 = associationDao.findById(byId.get().getAssId());
            byId1.ifPresent(activityGroup::setAssociation);
        }
        return activityGroup;
    }

    @Override
    public void delete(Integer id) {
        Optional<Activity> byId = activityDao.findById(id);
        if (byId.isPresent()) {
            //0代表未借用资源，1代表借用资源
            if (0 == byId.get().getAssetSign()) {
                //删除活动
                activityDao.deleteById(id);
            } else {
                //查询中间表信息并遍历
                List<ActAsset> byActId = actAssetDao.findByActId(id);
                byActId.forEach(actAsset -> {
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
                actAssetDao.deleteByActId(id);
                //删除活动信息
                activityDao.deleteById(id);

            }
        }
    }

    /**
     * 活动审核动态条件构建
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

    /**
     * 活动列表动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Activity> createSpecification(Map searchMap, List<Integer> integers) {

        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            //判断integers是不是为null
            if (!integers.isEmpty()) {
                //获取数据库中assId的值在integers中的数据
                CriteriaBuilder.In<Integer> assId = cb.in(root.get("assId"));
                integers.forEach(assId::value);
                predicateList.add(assId);
            }
            // 活动名称
            if (searchMap.get("act_name") != null && !"".equals(searchMap.get("act_name"))) {
                predicateList.add(cb.like(root.get("act_name").as(String.class), "%" + searchMap.get
                        ("act_name") + "%"));
            }
            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

        };

    }
}
