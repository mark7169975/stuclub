package com.ccut.yiyi.service;

import com.ccut.yiyi.model.Association;
import com.ccut.yiyi.model.Student;
import com.ccut.yiyi.model.group.AssoSimplify;
import com.ccut.yiyi.model.group.AssociationApply;
import com.ccut.yiyi.model.group.AssociationGroup;
import com.ccut.yiyi.model.group.StudentSimplify;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: AssociationService
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/22 14:47
 * @version: V1.0
 */
public interface AssociationService {
    /**
     * 社团申请service接口
     *
     * @param associationApply
     */
    void add(AssociationApply associationApply);

    /**
     * 社团条件查询接口 默认查询条件为null
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    Page<AssociationGroup> findSearch(Map whereMap, int page, int size);

    /**
     * UI页面查询社团信息
     *
     * @param page 页数
     * @param size 条数
     * @param id   大类id
     * @return 返回数据
     */
    Page<Association> findSearch(int page, int size, int id);

    /**
     * 通过社团类型typeCode查询其下所有社团
     *
     * @param code
     * @return
     */
    List<Association> findAssByCode(Integer code);

    /**
     * 根据社团id查询一个的接口
     *
     * @param id
     * @return
     */
    AssociationGroup findOne(Integer id);

    /**
     * 删除社团信息
     *
     * @param id
     */
    void deleteById(Integer id, String stuCode);

    /**
     * 修改社团信息
     *
     * @param associationGroup
     */
    void update(AssociationGroup associationGroup);

    /**
     * 通过社团id查询此社团下的所有学生
     *
     * @param assId 社团id
     * @return 返回数据
     */
    List<StudentSimplify> findOneAss(Integer assId);

    /**
     * 社团换届的service接口
     *
     * @param stuCode 需要换届的学生学号
     * @param assId   社团id
     */
    void changeManage(String stuCode, Integer assId);

    /**
     * 通过登录系统管理人员信息查询此管理加入的所有能被他管理的社团，如果是超级管理，则返回所有社团信息
     *
     * @param stuCode 学号
     * @param role    角色
     * @return 返回数据
     */
    List<Association> findByStuCodeAndRole(String stuCode, String role);

    /**
     * 审核中心，查询所有社团信息
     *
     * @return 返回数据
     */
    List<AssoSimplify> findAll();

    /**
     * 查找此社团所有管理人员信息
     *
     * @param assId 社团id
     * @return 返回数据
     */
    List<Student> findAllManage(Integer assId);
}
