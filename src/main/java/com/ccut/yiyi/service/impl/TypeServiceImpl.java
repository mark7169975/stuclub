package com.ccut.yiyi.service.impl;

import com.ccut.yiyi.dao.AssociationDao;
import com.ccut.yiyi.dao.AssociationTypeDao;
import com.ccut.yiyi.model.Association;
import com.ccut.yiyi.model.AssociationType;
import com.ccut.yiyi.model.group.TypeGroup;
import com.ccut.yiyi.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: TypeServiceImpl
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/21 17:17
 * @version: V1.0
 */
@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private AssociationTypeDao typeDao;
    @Autowired
    private AssociationDao associationDao;

    /**
     * 查询社团所有类型的service实现
     * @return
     */
    @Override
    public List<AssociationType> findAll() {
        return typeDao.findAll();
    }

    @Override
    public List<TypeGroup> findAllTypeGroup() {

        List<AssociationType> allType = typeDao.findAll();
        List<TypeGroup> collect = allType.stream().map(type -> {
            TypeGroup typeGroup = new TypeGroup();
            typeGroup.setAssociationType(type);
            List<Association> byTypeCode = associationDao.findByTypeCode(type.getTypeCode());
            typeGroup.setAssociationList(byTypeCode);
            return typeGroup;
        }).collect(Collectors.toList());
        System.out.println(collect);
        return collect;
    }
}
