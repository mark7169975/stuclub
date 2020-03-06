package com.ccut.yiyi.service;

import com.ccut.yiyi.model.Notice;
import com.ccut.yiyi.model.group.NoticeGroup;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: NoticeService    公告服务层接口
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/2/15 20:11
 * @version: V1.0
 */
public interface NoticeService {
    /**
     * 发布社团公告
     *
     * @param stuCode 发布公告的管理员学号
     * @param notice  公告内容
     */
    void add(String stuCode, Notice notice);

    /**
     * 条件查询+分页
     *
     * @param searchMap 查询条件
     * @param page      页码
     * @param rows      每页显示几条
     * @param stuCode   学号
     * @param role      角色
     * @return 返回数据
     */
    Page<NoticeGroup> findSearch(Map searchMap, int page, int rows, String stuCode, String role);

    /**
     * UI页面分页查询公告信息
     *
     * @param page 页码
     * @param rows 条数
     * @return  返回数据
     */
    Page<NoticeGroup> findSearch(int page, int rows);

    /**
     * 通过公告id查询一个公告详情
     *
     * @param id 公告id
     * @return 返回数据
     */
    Notice findOne(Integer id);

    /**
     * 修改公告信息
     *
     * @param stuCode 修改人学号
     * @param notice  修改信息
     */
    void update(String stuCode, Notice notice);

    /**
     * 删除公告信息
     *
     * @param notId 公告id
     */
    void delete(Integer notId);

    /**
     * UI首页查询6个公告信息
     *
     * @return 返回数据
     */
    List<NoticeGroup> findAll();

}
