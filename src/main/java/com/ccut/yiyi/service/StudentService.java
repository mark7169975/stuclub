package com.ccut.yiyi.service;

import com.ccut.yiyi.model.group.AssociationGroup;
import com.ccut.yiyi.model.group.StudentGroup;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * @ClassName: StudentService
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/18 17:02
 * @version: V1.0
 */
public interface StudentService {
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
     * 添加社团成员
     * @param studentGroup
     */
    void add(StudentGroup studentGroup);

    AssociationGroup findById(Integer id);

    void deleteById(Integer id);
}
