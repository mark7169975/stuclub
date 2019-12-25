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
     * 增加
     *
     * @param associationApply
     */
    void add(AssociationApply associationApply);

    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    Page<AssociationGroup> findSearch(Map whereMap, int page, int size);

    /**
     * //获取页数
     *
     * @return
     */

    Long getTotle();

    //通过code查询社团
    List<Association> findAssByCode(Integer code);

    AssociationGroup findOne(Integer id);

    void deleteById(Integer id);
}
