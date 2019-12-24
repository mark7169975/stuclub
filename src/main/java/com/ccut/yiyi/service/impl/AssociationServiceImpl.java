package com.ccut.yiyi.service.impl;

import com.ccut.yiyi.dao.AssociationDao;
import com.ccut.yiyi.dao.AssociationTypeDao;
import com.ccut.yiyi.dao.StudentDao;
import com.ccut.yiyi.model.Association;
import com.ccut.yiyi.model.Student;
import com.ccut.yiyi.model.group.AssociationApply;
import com.ccut.yiyi.model.group.AssociationGroup;
import com.ccut.yiyi.service.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: AssociationServiceImpl
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/22 14:47
 * @version: V1.0
 */
@Service
public class AssociationServiceImpl implements AssociationService {
    @Autowired
    private AssociationDao associationDao;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private AssociationTypeDao associationTypeDao;

    @Override
    public void add(AssociationApply associationApply) {
        //设置社团的信息
        Association association = new Association();
        association.setAssName(associationApply.getAssName());
        association.setStuCode(associationApply.getStuCode());
        association.setAssDescription(associationApply.getAssDescription());
        association.setTypeCode(associationApply.getTypeCode());
        //添加社团
        Association save = associationDao.save(association);
        //设置社长的学生信息
        Student student = new Student();
        student.setStuCode(associationApply.getStuCode());
        student.setStuName(associationApply.getStuName());
        student.setStuCollege(associationApply.getStuCollege());
        student.setStuMajor(associationApply.getStuMajor());
        student.setStuPwd(associationApply.getStuCode());
        student.setAssociationId(save.getAssId());
        student.setRoleCode(2);//社团管理人员角色编号为2
        studentDao.save(student);

    }

    Long total = 0L;

    @Override
    public List<AssociationGroup> findSearch(Map whereMap, int page, int size) {
        // Specification<AssociationGroup> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        //Page<AssociationGroup> all = associationDao.findAll(specification, pageRequest);
        Page<Association> all = associationDao.findAll(pageRequest);
        List<Association> content = all.getContent();
        total = all.getTotalElements();
        List<AssociationGroup> collect = content.stream().map(ass -> {
            AssociationGroup associationGroup = new AssociationGroup();
            associationGroup.setAssociation(ass);
            associationGroup.setStudent(studentDao.findByStuCode(ass.getStuCode()));
            associationGroup.setAssociationType(associationTypeDao.findByTypeCode(ass.getTypeCode()));
            return associationGroup;
        }).collect(Collectors.toList());

        return collect;
    }

    @Override
    public Long getTotle() {
        return total;
    }

    @Override
    public List<Association> findAssByCode(Integer code) {
        return associationDao.findByTypeCode(code);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<AssociationGroup> createSpecification(Map searchMap) {

        return new Specification<AssociationGroup>() {

            @Override
            public Predicate toPredicate(Root<AssociationGroup> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // 社团名称
                if (searchMap.get("ass_name") != null && !"".equals(searchMap.get("ass_name"))) {
                    predicateList.add(cb.like(root.get("ass_name").as(String.class), "%" + (String) searchMap.get
                            ("ass_name") + "%"));
                }
                // 社长学号
                if (searchMap.get("stu_code") != null && !"".equals(searchMap.get("stu_code"))) {
                    predicateList.add(cb.like(root.get("stu_code").as(String.class), "%" + (String) searchMap.get
                            ("stu_code") + "%"));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }
}
