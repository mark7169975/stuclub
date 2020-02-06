package com.ccut.yiyi.service;

import com.ccut.yiyi.model.Association;
import com.ccut.yiyi.model.group.AssociationApply;
import com.ccut.yiyi.model.group.AssociationGroup;
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
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    Page<AssociationGroup> findSearch(Map whereMap, int page, int size);

    /**
     * 通过社团类型typeCode查询其下所有社团
     * @param code
     * @return
     */
    List<Association> findAssByCode(Integer code);

    /**
     * 根据社团id查询一个的接口
     * @param id
     * @return
     */
    AssociationGroup findOne(Integer id);

    /**
     * 删除社团信息
     * @param id
     */
    void deleteById(Integer id,String stuCode);

    /**
     * 修改社团信息
     * @param associationGroup
     */
    void update(AssociationGroup associationGroup);
}
