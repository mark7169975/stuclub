package com.ccut.yiyi.service;

import com.ccut.yiyi.model.AssociationType;
import com.ccut.yiyi.model.group.TypeGroup;

import java.util.List;

/**
 * @ClassName: TypeService
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/21 17:15
 * @version: V1.0
 */
public interface TypeService {
    /**
     * 查询社团所有类型
     * @return
     */
    List<AssociationType> findAll();

    List<TypeGroup> findAllTypeGroup();
}
