package com.ccut.yiyi.service;

import com.ccut.yiyi.model.Activity;
import com.ccut.yiyi.model.group.ActivityGroup;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * @ClassName: ActivityService  活动业务层接口
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/2/15 15:15
 * @version: V1.0
 */
public interface ActivityService {
    /**
     * 添加社团活动
     *
     * @param loginStuCode 添加活动的管理员学号
     * @param remain       添加资产数量
     * @param activity     活动信息
     */
    void add(String loginStuCode, Integer[] remain, Activity activity);

    /**
     * 如果不需要资源
     *
     * @param loginStuCode 学号
     * @param activity     活动信息
     */
    void add(String loginStuCode, Activity activity);

    /**
     * 活动审核页面的分页多条件查询
     *
     * @param searchMap 查询条件
     * @param page      页数
     * @param rows      条数
     * @return 返回数据
     */
    Page<ActivityGroup> searchApproval(Map searchMap, int page, int rows);

    /**
     * 修改审批状态
     *
     * @param actId 活动id
     * @param mark  状态码
     */
    void changeApproval(Integer actId, Integer mark);
}
