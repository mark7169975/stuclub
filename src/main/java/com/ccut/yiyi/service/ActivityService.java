package com.ccut.yiyi.service;

import com.ccut.yiyi.model.Activity;
import com.ccut.yiyi.model.group.ActivityGroup;
import org.springframework.data.domain.Page;

import java.util.List;
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

    /**
     * 活动列表，查询此管理员所有的社团活动列表
     *
     * @param searchMap 查询条件
     * @param page      页数
     * @param rows      每页几条
     * @param stuCode   学号
     * @param role      角色
     * @return 返回数据
     */
    Page<ActivityGroup> findSearch(Map searchMap, int page, int rows, String stuCode, String role);

    /**
     * UI页面查询所有社团活动信息
     *
     * @param page 页码
     * @param rows 条数
     * @return 返回数据
     */
    Page<ActivityGroup> findSearch(int page, int rows);

    /**
     * UI首页查询6个活动信息
     *
     * @return 返回数据
     */
    List<ActivityGroup> findAll();

    /**
     * 重新申请活动
     * @param actId 活动id
     */
    void reapply(Integer actId);

    /**
     * 查询一个活动详细信息
     * @param id    活动id
     * @return  返回数据
     */
    ActivityGroup findOne(Integer id);

    /**
     * 通过活动id删除活动信息
     * @param id    活动id
     */
    void delete(Integer id);
}
